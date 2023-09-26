package br.com.desafio.precadastroclientes.cliente.model.dto;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePessoaJuridicaRequest {

    @NotNull
    @Size(min = 14, max = 14)
    private String cnpj;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ETipoCliente tipoCliente;

    @NotNull
    @Size(max = 50)
    private String razaoSocial;

    @NotNull
    @Size(max = 4)
    private String mcc;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpfContato;

    @NotNull
    @Size(max = 50)
    private String nomeContato;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String emailContato;
}
