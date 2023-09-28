package br.com.desafio.precadastroclientes.cliente.service;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.exception.ClienteException;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import br.com.desafio.precadastroclientes.cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteFilaService filaService;;

    private Cliente getById(Long id) {
        return clienteRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
    }

    @Transactional
    public ClienteResponse criarCliente(ClienteRequest clienteRequest) {
        var tipoCliente = clienteRequest.getTipoCliente();
        if (tipoCliente == ETipoCliente.PESSOA_JURIDICA) {
            validarCnpj(clienteRequest);
        } else if (tipoCliente == ETipoCliente.PESSOA_FISICA) {
            validarCpf(clienteRequest);
        } else {
            throw new ClienteException("Tipo de cliente não suportado");
        }

        var cliente = Cliente.of(clienteRequest);
        clienteRepository.save(cliente);
        log.info("Cliente criado com sucesso. UUID: {}", cliente.getUuid());
        filaService.enfileirar(ClienteResponse.of(cliente));
        return ClienteResponse.of(clienteRequest);
    }

    @Transactional
    public ClienteResponse atualizarCliente(Long id, ClienteRequest clienteRequest) {
        var clienteExistente = this.getById(id);
        if (clienteRequest.getTipoCliente() == ETipoCliente.PESSOA_JURIDICA) {
            this.validarCnpjParaAtualizacao(clienteRequest, clienteExistente);
        } else {
            this.validarCpfParaAtualizacao(clienteRequest, clienteExistente);
        }

        clienteExistente.atualizarCliente(clienteRequest);
        var cliente = clienteRepository.save(clienteExistente);
        log.info("Cliente UUID: {} atualizado com sucesso.", clienteExistente.getUuid());
        filaService.atualizarClienteNaFila(cliente);
        return ClienteResponse.of(clienteExistente);
    }

    public List<ClienteResponse> buscarTodosClientes() {
        var clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
        log.info("Cliente deletado com sucesso.");
    }

    private void validarCpfParaAtualizacao(ClienteRequest clienteRequest, Cliente clienteExistente) {
        log.debug("Validando CPF para atualização: {}", clienteRequest.getCpfPessoa());
        clienteRepository.findByCpfPessoa(clienteRequest.getCpfPessoa()).ifPresent(clienteComCnpjExistente -> {
            if (!clienteComCnpjExistente.getId().equals(clienteExistente.getId())) {
                throw new ClienteException("CPF já cadastrado por outro cliente.");
            }
        });
    }

    private void validarCnpjParaAtualizacao(ClienteRequest clienteRequest, Cliente clienteExistente) {
        log.debug("Validando CNPJ para atualização: {}", clienteRequest.getCnpj());
        clienteRepository.findByCnpj(clienteRequest.getCnpj()).ifPresent(clienteComCnpjExistente -> {
            if (!clienteComCnpjExistente.getId().equals(clienteExistente.getId())) {
                throw new ClienteException("CNPJ já cadastrado por outro cliente.");
            }
        });
    }

    private void validarCnpj(ClienteRequest clienteRequest) {
        log.info("Validando CNPJ pra criação de cliente.");
        if (clienteRepository.existsByCnpj(clienteRequest.getCnpj())) {
            throw new ClienteException("CNPJ já cadastrado.");
        }
    }

    private void validarCpf(ClienteRequest clienteRequest) {
        log.info("Validando CPF pra criação de cliente.");
        if (clienteRepository.existsByCpfPessoa(clienteRequest.getCpfPessoa())) {
            throw new ClienteException("CPF já cadastrado.");
        }
    }
}