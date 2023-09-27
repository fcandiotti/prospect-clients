package br.com.desafio.precadastroclientes.cliente.model.dto;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePessoaFisicaResponse {

    private String cpf;
    private String mcc;
    private String nome;
    private String email;
    private ETipoCliente tipoCliente;

    public static ClientePessoaFisicaResponse of(ClientePessoaFisicaRequest request) {
        var cliente = new ClientePessoaFisicaResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public static ClientePessoaFisicaResponse of(Cliente request) {
        var cliente = new ClientePessoaFisicaResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }
}