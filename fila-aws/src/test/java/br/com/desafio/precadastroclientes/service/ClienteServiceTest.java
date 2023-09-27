package br.com.desafio.precadastroclientes.service;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaFisicaRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaFisicaResponse;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaJuridicaRequest;
import br.com.desafio.precadastroclientes.cliente.repository.ClienteRepository;
import br.com.desafio.precadastroclientes.cliente.service.ClienteFilaService;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteFilaService clienteFilaService;

    @Test
    public void criarClientePf_deveCriarClientePf_seCpfValido() {
        var request = umClientePessoaFisicaRequest();

        when(clienteRepository.findByCpf("12345678910")).thenReturn(Optional.empty());

        var response = clienteService.criarClientePf(request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCpf());
        assertEquals(ETipoCliente.PESSOA_FISICA, response.getTipoCliente());
        assertEquals("1234", response.getMcc());
        assertEquals("teste@teste.com.br", response.getEmail());
        assertEquals("Teste", response.getNome());

        verify(clienteRepository).findByCpf(eq("12345678910"));
    }

    @Test
    public void criarClientePf_deveLancarException_seCpfJaUtilizado() {
        var request = new ClientePessoaFisicaRequest();
        request.setCpf("12345678910");

        when(clienteRepository.findByCpf("12345678910")).thenReturn(Optional.of(new Cliente()));

        assertThrows(IntegrationException.class, () -> clienteService.criarClientePf(request));

        verify(clienteRepository).findByCpf(eq("12345678910"));

    }

    @Test
    public void criarClientePj_deveCriarClientePf_seCnpjValido() {
        var request = umClientePessoaJuridicaRequest();

        when(clienteRepository.findByCnpj("12345678910")).thenReturn(Optional.empty());

        var response = clienteService.criarClientePj(request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCpfContato());
        assertEquals(ETipoCliente.PESSOA_JURIDICA, response.getTipoCliente());
        assertEquals("1234", response.getMcc());
        assertEquals("teste@teste.com.br", response.getEmailContato());
        assertEquals("Teste", response.getNomeContato());
        assertEquals("Teste", response.getRazaoSocial());
        assertEquals("12345678910", response.getCnpj());

        verify(clienteRepository).findByCnpj(eq("12345678910"));
    }

    @Test
    public void criarClientePj_deveLancarException_seCnpjJaUtilizado() {
        var request = new ClientePessoaJuridicaRequest();
        request.setCnpj("12345678910");

        when(clienteRepository.findByCnpj("12345678910")).thenReturn(Optional.of(new Cliente()));

        assertThrows(IntegrationException.class, () -> clienteService.criarClientePj(request));

        verify(clienteRepository).findByCnpj(eq("12345678910"));
    }

    @Test
    public void atualizarClientePj_deveAtualizarClientePj_seParametrosValidos() {
        var id = 1L;
        var request = ClientePessoaJuridicaRequest
                .builder()
                .cnpj("12345678901234")
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCnpj(request.getCnpj())).thenReturn(Optional.empty());

        var response = clienteService.atualizarClientePj(id, request);

        assertNotNull(response);
        assertEquals("12345678901234", response.getCnpj());

        verify(clienteRepository).findById(eq(id));
        verify(clienteRepository).findByCnpj(eq(request.getCnpj()));
    }

    @Test
    public void atualizarClientePj_deveLancarException_seParametrosInvalidos() {
        var id = 1L;
        var request = ClientePessoaJuridicaRequest
                .builder()
                .cnpj("12345678901234")
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();
        var outroCliente =  Cliente
                .builder()
                .id(2L)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCnpj(request.getCnpj())).thenReturn(Optional.of(outroCliente));

        var exception = assertThrows(IntegrationException.class, () -> clienteService.atualizarClientePj(id, request));

        assertEquals("CNPJ já cadastrado por outro cliente.", exception.getMessage());
        verify(clienteRepository).findById(eq(id));
        verify(clienteRepository).findByCnpj(eq(request.getCnpj()));
    }

    @Test
    public void atualizarClientePf_deveAtualizarClientPf_seParametrosInvalidos() {
        var id = 1L;
        var request = ClientePessoaFisicaRequest
                .builder()
                .cpf("12345678910")
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpf(request.getCpf())).thenReturn(Optional.empty());

        ClientePessoaFisicaResponse response = clienteService.atualizarClientePf(id, request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCpf());

        verify(clienteRepository).findById(eq(id));
        verify(clienteRepository).findByCpf(eq(request.getCpf()));
    }

    @Test
    public void atualizarClientePf_deveLancarException_seParametrosInvalidos() {
        var id = 1L;
        var request = ClientePessoaFisicaRequest
                .builder()
                .cpf("12345678910")
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();
        var outroCliente =  Cliente
                .builder()
                .id(2L)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpf(request.getCpf())).thenReturn(Optional.of(outroCliente));

        var exception = assertThrows(IntegrationException.class, () ->
                clienteService.atualizarClientePf(id, request));

        assertEquals("CPF já cadastrado por outro cliente.", exception.getMessage());
    }

    @Test
    public void buscarTodosClientes_deveRetornarListaDeClientes_quandoSolicitado() {
        var clientePf = Cliente.builder().id(1L).nome("Cliente 1").tipoCliente(ETipoCliente.PESSOA_FISICA).build();
        var clientPj = Cliente.builder().id(2L).nome("Cliente 2").tipoCliente(ETipoCliente.PESSOA_JURIDICA).build();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(clientePf, clientPj));

        var responses = clienteService.buscarTodosClientes();

        assertEquals(2, responses.size());
        assertEquals("Cliente 1", responses.get(0).getNome());
        assertEquals("Cliente 2", responses.get(1).getNome());

        verify(clienteRepository).findAll();
    }

    @Test
    void deleteById_deveDeletarCliente_quandoClienteEncontrado() {
        var id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        clienteService.deleteById(id);

        verify(clienteRepository).deleteById(id);
    }

    @Test
    void deleteById_deveLancarException_quandoClienteNaoEncontrado() {
        var id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        var exception = assertThrows(RuntimeException.class, () ->
                clienteService.deleteById(id));

        assertEquals("Cliente não encontrado.", exception.getMessage());

        verify(clienteRepository, never()).deleteById(id);
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
}
