package br.com.desafio.precadastroclientes.cliente.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.sqs.SqsClient;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class AwsConfig {

    @Value("${aws.secrets.name}")
    private String secretName;

    @Value("${aws.secrets.name.h2}")
    private String secretDb;

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder().build();
    }

    public Map<String, String> getAwsCredentials() {
        return getCredentialsFromSecretsManager(secretName);
    }

    public Map<String, String> getDatabaseCredentials() {
        return getCredentialsFromSecretsManager(secretDb);
    }

    @Bean
    public SqsClient sqsClient() {
        Map<String, String> credentialsMap = getAwsCredentials();
        String accessKeyId = credentialsMap.get("aws.accessKeyId");
        String secretKey = credentialsMap.get("aws.secretKey");

        return SqsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
                .build();
    }

    @Bean
    public DataSource dataSource() {
        Map<String, String> dbCredentials = getDatabaseCredentials();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbCredentials.get("spring.datasource.url"));
        hikariConfig.setDriverClassName(dbCredentials.get("spring.datasource.driver-class-name"));
        hikariConfig.setUsername(dbCredentials.get("spring.datasource.username"));
        hikariConfig.setPassword(dbCredentials.get("spring.datasource.password"));

        return new HikariDataSource(hikariConfig);
    }

    private Map<String, String> getCredentialsFromSecretsManager(String secretId) {
        SecretsManagerClient smClient = secretsManagerClient();
        try {
            String secretString = smClient.getSecretValue(GetSecretValueRequest.builder().secretId(secretId).build()).secretString();
            return new ObjectMapper().readValue(secretString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao deserializar o JSON do Secrets Manager para o segredo: " + secretId, e);
        }
    }
}