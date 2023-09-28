package br.com.desafio.precadastroclientes.cliente.service;

import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsService {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public void sendMessage(Cliente dto)  {
        try {
            var messageBody = objectMapper.writeValueAsString(dto);
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build());
            log.info("Enviando dados do cliente para fila {}", dto.getUuid());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter DTO para JSON", e);
        }
    }

    public ReceiveMessageResponse receiveMessages() {
        return sqsClient
                .receiveMessage(builder -> builder.queueUrl(queueUrl).maxNumberOfMessages(1));
    }
}
