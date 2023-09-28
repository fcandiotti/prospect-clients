package br.com.desafio.precadastroclientes.cliente.model.dto;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.UUID;
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private UUID uuid;
    private String cpfPessoa;
    private String mcc;
    private String nomePessoa;
    private String emailPessoa;
    private String cnpj;
    private String razaoSocial;
    private String cpfContato;
    private String nomeContato;
    private String emailContato;
    private ETipoCliente tipoCliente;

    public static ClienteResponse of(ClienteRequest request) {
        var cliente = new ClienteResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public static ClienteResponse of(Cliente request) {
        var cliente = new ClienteResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }
}