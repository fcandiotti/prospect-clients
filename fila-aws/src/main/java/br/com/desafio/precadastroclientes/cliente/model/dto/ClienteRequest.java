package br.com.desafio.precadastroclientes.cliente.model.dto;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private ETipoCliente tipoCliente;
    private String mcc;
    private String cnpj;
    private String razaoSocial;
    private String cpfContato;
    private String nomeContato;
    private String emailContato;
    private String cpfPessoa;
    private String nomePessoa;
    private String emailPessoa;
}
