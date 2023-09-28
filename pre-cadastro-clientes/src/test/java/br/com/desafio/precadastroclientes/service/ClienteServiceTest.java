package br.com.desafio.precadastroclientes.service;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.exception.ClienteException;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.repository.ClienteRepository;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
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

    @Test
    public void criarCliente_deveCriarCliente_seCpfValido() {
        var request = umClienteRequest();

        when(clienteRepository.existsByCpfPessoa("12345678910")).thenReturn(false);

        var response = clienteService.criarCliente(request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCpfPessoa());
        assertEquals(ETipoCliente.PESSOA_FISICA, response.getTipoCliente());
        assertEquals("1234", response.getMcc());
        assertEquals("teste@teste.com.br", response.getEmailPessoa());
        assertEquals("Teste", response.getNomePessoa());

        verify(clienteRepository).existsByCpfPessoa(eq("12345678910"));
    }

    @Test
    public void criarCliente_deveCriarCliente_seCnpjValido() {
        var request = umClienteRequestCnpj();

        when(clienteRepository.existsByCnpj("12345678910")).thenReturn(false);

        var response = clienteService.criarCliente(request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCnpj());
        assertEquals(ETipoCliente.PESSOA_JURIDICA, response.getTipoCliente());
        assertEquals("1234", response.getMcc());
        assertEquals("Teste", response.getRazaoSocial());

        verify(clienteRepository).existsByCnpj(eq("12345678910"));
    }

    @Test
    public void criarCliente_deveLancarException_seCpfJaUtilizado() {
        var request = new ClienteRequest();
        request.setTipoCliente(ETipoCliente.PESSOA_FISICA);
        request.setCpfPessoa("12345678910");

        when(clienteRepository.existsByCpfPessoa("12345678910")).thenReturn(true);

        var exception = assertThrows(ClienteException.class, () ->
                clienteService.criarCliente(request));

        assertEquals("CPF já cadastrado.", exception.getMessage());
    }

    @Test
    public void criarCliente_deveLancarException_seCnpjJaUtilizado() {
        var request = new ClienteRequest();
        request.setTipoCliente(ETipoCliente.PESSOA_JURIDICA);
        request.setCnpj("12345678910");

        when(clienteRepository.existsByCnpj("12345678910")).thenReturn(true);

        var exception = assertThrows(ClienteException.class, () ->
                clienteService.criarCliente(request));

        assertEquals("CNPJ já cadastrado.", exception.getMessage());
    }

    @Test
    public void atualizarCliente_deveAtualizarCliente_seCpfValido() {
        var id = 1L;
        var request = ClienteRequest
                .builder()
                .cpfPessoa("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpfPessoa(request.getCpfPessoa())).thenReturn(Optional.empty());

        var response = clienteService.atualizarCliente(id, request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCpfPessoa());

        verify(clienteRepository).findById(eq(id));
        verify(clienteRepository).findByCpfPessoa(eq(request.getCpfPessoa()));
    }

    @Test
    public void atualizarCliente_deveLancarException_seCpfJaUtilizado() {
        var id = 1L;
        var request = ClienteRequest
                .builder()
                .cpfPessoa("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
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
        when(clienteRepository.findByCpfPessoa(request.getCpfPessoa())).thenReturn(Optional.of(outroCliente));

        var exception = assertThrows(ClienteException.class, () ->
                clienteService.atualizarCliente(id, request));

        assertEquals("CPF já cadastrado por outro cliente.", exception.getMessage());
    }

    @Test
    public void atualizarCliente_deveAtualizarCliente_seCnpjValido() {
        var id = 1L;
        var request = ClienteRequest
                .builder()
                .cnpj("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .build();
        var clienteExistente = Cliente
                .builder()
                .id(id)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCnpj(request.getCnpj())).thenReturn(Optional.empty());

        var response = clienteService.atualizarCliente(id, request);

        assertNotNull(response);
        assertEquals("12345678910", response.getCnpj());

        verify(clienteRepository).findById(eq(id));
        verify(clienteRepository).findByCnpj(eq(request.getCnpj()));
    }

    @Test
    public void atualizarCliente_deveLancarException_seCnpjJaUtilizado() {
        var id = 1L;
        var request = ClienteRequest
                .builder()
                .cnpj("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
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

        var exception = assertThrows(ClienteException.class, () ->
                clienteService.atualizarCliente(id, request));

        assertEquals("CNPJ já cadastrado por outro cliente.", exception.getMessage());
    }

    @Test
    public void buscarTodosClientes_deveRetornarListaDeClientes_quandoSolicitado() {
        var clientePf = Cliente.builder().id(1L).nomePessoa("Cliente 1").tipoCliente(ETipoCliente.PESSOA_FISICA).build();
        var clientPj = Cliente.builder().id(2L).nomePessoa("Cliente 2").tipoCliente(ETipoCliente.PESSOA_JURIDICA).build();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(clientePf, clientPj));

        var responses = clienteService.buscarTodosClientes();

        assertEquals(2, responses.size());
        assertEquals("Cliente 1", responses.get(0).getNomePessoa());
        assertEquals("Cliente 2", responses.get(1).getNomePessoa());

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

    private static ClienteRequest umClienteRequest() {
        return ClienteRequest.builder()
                .cpfPessoa("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_FISICA)
                .mcc("1234")
                .emailPessoa("teste@teste.com.br")
                .nomePessoa("Teste")
                .build();
    }

    private static ClienteRequest umClienteRequestCnpj() {
        return ClienteRequest.builder()
                .cnpj("12345678910")
                .tipoCliente(ETipoCliente.PESSOA_JURIDICA)
                .mcc("1234")
                .razaoSocial("Teste")
                .build();
    }
}
