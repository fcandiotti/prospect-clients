package br.com.desafio.precadastroclientes.cliente.model.dto;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String cpf;
    private String mcc;
    private String nome;
    private String email;
    private String cnpj;
    private String razaoSocial;
    private String cpfContato;
    private String nomeContato;
    private String emailContato;
    private ETipoCliente tipoCliente;

    public static ClienteResponse of(Cliente request) {
        var cliente = new ClienteResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public static ClienteResponse of(ClientePessoaJuridicaRequest request) {
        var cliente = new ClienteResponse();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }
}