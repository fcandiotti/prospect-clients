package br.com.desafio.precadastroclientes.controller;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(clienteService.criarCliente(umClienteRequest()))
                .thenReturn(umClienteResponse());

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(umClienteRequest())))
                .andExpect(status().isCreated());
    }

    private static ClienteRequest umClienteRequest() {
        return ClienteRequest.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }

    private static ClienteResponse umClienteResponse() {
        return ClienteResponse.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }
}
