package br.com.desafio.precadastroclientes.controller;

import br.com.desafio.precadastroclientes.cliente.controller.ClienteController;
import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    @SneakyThrows
    public void buscarTodosClientes_deveRetornarLista_quandoExistirClientes() {
        var clientes = List.of(umClienteResponsePessoaFisica(), umClienteResponsePessoaJuridica());
        doReturn(clientes).when(clienteService).buscarTodosClientes();

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipoCliente").value("PESSOA_FISICA"))
                .andExpect(jsonPath("$[0].mcc").value("1234"))
                .andExpect(jsonPath("$[1].tipoCliente").value("PESSOA_JURIDICA"))
                .andExpect(jsonPath("$[1].mcc").value("1234"));
    }

    @Test
    @SneakyThrows
    public void buscarTodosClientes_deveRetornarListaVazia_quandoNaoExistirClientes() {
        var clientes = List.of();
        doReturn(clientes).when(clienteService).buscarTodosClientes();

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    public void criarCliente_deveCriarClientePessoaFisica_seTodosOsCamposInformadosCorretamente() {
        doReturn(umClienteResponsePessoaFisica()).when(clienteService).criarCliente(any(ClienteRequest.class));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(umClienteRequestPessoaFisica())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoCliente").value("PESSOA_FISICA"))
                .andExpect(jsonPath("$.mcc").value("1234"))
                .andExpect(jsonPath("$.emailPessoa").value("teste@teste.com.br"))
                .andExpect(jsonPath("$.nomePessoa").value("Teste"));
    }

    @Test
    @SneakyThrows
    public void criarCliente_deveCriarClientePessoaJuridica_seTodosOsCamposInformadosCorretamente() {
        doReturn(umClienteResponsePessoaJuridica()).when(clienteService).criarCliente(any(ClienteRequest.class));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(umClienteRequestPessoaJuridica())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoCliente").value("PESSOA_JURIDICA"))
                .andExpect(jsonPath("$.mcc").value("1234"))
                .andExpect(jsonPath("$.cnpj").value("12345678901111"))
                .andExpect(jsonPath("$.razaoSocial").value("Teste Ltda"))
                .andExpect(jsonPath("$.cpfContato").value("12345678910"))
                .andExpect(jsonPath("$.nomeContato").value("Teste"))
                .andExpect(jsonPath("$.emailContato").value("teste@teste.com.br"));
    }

    @Test
    @SneakyThrows
    public void atualizarClientePj_deveRetornarClienteAtualizado_seAtualizacaoBemSucedida() {
        var clienteId = 1L;
        var clienteRequest = umClienteRequestPessoaFisica();
        var clienteAtualizado = umClienteResponsePessoaFisicaAtualizado();

        doReturn(clienteAtualizado).when(clienteService).atualizarCliente(eq(clienteId), any(ClienteRequest.class));

        mockMvc.perform(put("/api/clientes/" + clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpfContato").value("00000000000"));
    }

    @Test
    @SneakyThrows
    public void deleteClienteById_DeveDeletarCliente_seClienteExistir() {
        var id = 1L;
        doNothing().when(clienteService).deleteById(id);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void deleteClienteById_deveRetornarNotFound_seClienteNaoExistir() {
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNotFound());
    }

    private static ClienteRequest umClienteRequestPessoaFisica() {
        return ClienteRequest.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }

    private static ClienteResponse umClienteResponsePessoaFisica() {
        return ClienteResponse.builder()
                .cpfContato("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }

    private static ClienteResponse umClienteResponsePessoaFisicaAtualizado() {
        return ClienteResponse.builder()
                .cpfContato("00000000000")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }

    private static ClienteRequest umClienteRequestPessoaJuridica() {
        return ClienteRequest.builder()
                .mcc("1234")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .cnpj("12345678901111")
                .cpfContato("12345678910")
                .nomeContato("Teste")
                .razaoSocial("Teste Ltda")
                .emailContato("teste@teste.com.br")
                .build();
    }

    private static ClienteResponse umClienteResponsePessoaJuridica() {
        return ClienteResponse.builder()
                .mcc("1234")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .cnpj("12345678901111")
                .cpfContato("12345678910")
                .nomeContato("Teste")
                .razaoSocial("Teste Ltda")
                .emailContato("teste@teste.com.br")
                .build();
    }
}
