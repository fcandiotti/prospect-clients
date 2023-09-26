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
public class ClientePessoaJuridicaResponse {

    private String cnpj;
    private String razaoSocial;
    private String mcc;
    private String cpfContato;
    private String nomeContato;
    private String emailContato;
    private ETipoCliente tipoCliente;

    public static ClientePessoaJuridicaResponse of(ClientePessoaJuridicaRequest request) {
        var cliente = new ClientePessoaJuridicaResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public static ClientePessoaJuridicaResponse of(Cliente request) {
        var cliente = new ClientePessoaJuridicaResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }
}