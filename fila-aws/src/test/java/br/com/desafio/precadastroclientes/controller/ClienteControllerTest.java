package br.com.desafio.precadastroclientes.controller;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaFisicaRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaFisicaResponse;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaJuridicaRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaJuridicaResponse;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    @SneakyThrows
    public void criarClientPf_deveRetornar201Created_sePathCorreto() {
        when(clienteService.criarClientePf(umClientePessoaFisicaRequest()))
                .thenReturn(umClientePesssoaFisicaResponse());

        mockMvc.perform(post("/api/clientes/pf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(umClientePessoaFisicaRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void criarClientPj_deveRetornar201Created_sePathCorreto() {
        when(clienteService.criarClientePj(umClientePessoaJuridicaRequest()))
                .thenReturn(umClientePessoaJuridicaResponse());

        mockMvc.perform(post("/api/clientes/pj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(umClientePessoaJuridicaRequest())))
                .andExpect(status().isCreated());
    }

    private static ClientePessoaFisicaRequest umClientePessoaFisicaRequest() {
        return ClientePessoaFisicaRequest.builder()
                .cpf("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .email("teste@teste.com.br")
                .nome("Teste")
                .build();
    }

    private static ClientePessoaFisicaResponse umClientePesssoaFisicaResponse() {
        return ClientePessoaFisicaResponse.builder()
                .cpf("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .email("teste@teste.com.br")
                .nome("Teste")
                .build();
    }

    private static ClientePessoaJuridicaRequest umClientePessoaJuridicaRequest() {
        return ClientePessoaJuridicaRequest.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .mcc("1234")
                .emailContato("teste@teste.com.br")
                .nomeContato("Teste")
                .cnpj("12345678910")
                .razaoSocial("Teste")
                .build();
    }

    private static ClientePessoaJuridicaResponse umClientePessoaJuridicaResponse() {
        return ClientePessoaJuridicaResponse.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .mcc("1234")
                .emailContato("teste@teste.com.br")
                .nomeContato("Teste")
                .cnpj("12345678910")
                .razaoSocial("Teste")
                .build();
    }
}
