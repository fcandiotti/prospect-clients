package br.com.desafio.precadastroclientes.model;

import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteResponseTest {

    @Test
    public void of_deveConverterUmClienteRequest_quandoSolicitado() {
        var clienteRequest = Cliente.builder()
                .nomePessoa("Maria")
                .emailPessoa("maria@teste.com")
                .build();

        var clienteResponse = ClienteResponse.of(clienteRequest);

        Assertions.assertEquals(clienteRequest.getNomePessoa(), clienteResponse.getNomePessoa());
        Assertions.assertEquals(clienteRequest.getEmailPessoa(), clienteResponse.getEmailPessoa());
    }

    @Test
    public void of_deveConverterUmCliente_quandoSolicitado() {
        var cliente = Cliente.builder()
                .nomePessoa("Maria")
                .emailPessoa("maria@teste.com")
                .build();
        var clienteResponse = ClienteResponse.of(cliente);

        Assertions.assertEquals(cliente.getNomePessoa(), clienteResponse.getNomePessoa());
        Assertions.assertEquals(cliente.getEmailPessoa(), clienteResponse.getEmailPessoa());
    }
}
