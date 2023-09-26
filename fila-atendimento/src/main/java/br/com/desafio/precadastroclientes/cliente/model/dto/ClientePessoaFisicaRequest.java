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
public class ClientePessoaFisicaRequest {

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ETipoCliente tipoCliente;

    @NotNull
    @Size(max = 4)
    private String mcc;

    @NotNull
    @Size(max = 50)
    private String nome;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String email;
}