package br.com.desafio.precadastroclientes.cliente.service;

import br.com.desafio.precadastroclientes.cliente.aws.SqsService;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.*;
import br.com.desafio.precadastroclientes.cliente.repository.ClienteRepository;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final SqsService sqsService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, SqsService sqsService) {
        this.clienteRepository = clienteRepository;
        this.sqsService = sqsService;
    }

    public ClientePessoaFisicaResponse criarClientePf(ClientePessoaFisicaRequest clienteRequest) {
        validarCpf(clienteRequest);
        var pf = Cliente.of(clienteRequest);
        clienteRepository.save(pf);
        sqsService.sendMessagePf(pf);
       return  ClientePessoaFisicaResponse.of(clienteRequest);
    }

    public ClientePessoaJuridicaResponse criarClientePj(ClientePessoaJuridicaRequest clienteRequest) {
        validarCnpj(clienteRequest);
        var pj = Cliente.of(clienteRequest);
        clienteRepository.save(pj);
        sqsService.sendMessagePf(pj);

        return ClientePessoaJuridicaResponse.of(clienteRequest);
    }

    public ClientePessoaJuridicaResponse atualizarClientePj(Long id, ClientePessoaJuridicaRequest clienteRequest) {
        var clienteExistente = clienteRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        validarCnpjParaAtualizacao(clienteRequest, clienteExistente);
        clienteExistente.atualizarClientePj(clienteRequest);
        clienteRepository.save(clienteExistente);

        return ClientePessoaJuridicaResponse.of(clienteExistente);
    }

    public ClientePessoaFisicaResponse atualizarClientePf(Long id, ClientePessoaFisicaRequest clienteRequest) {
        var clienteExistente = clienteRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        validarCpfParaAtualizacao(clienteRequest, clienteExistente);
        clienteExistente.atualizarClientePf(clienteRequest);
        clienteRepository.save(clienteExistente);

        return ClientePessoaFisicaResponse.of(clienteExistente);
    }

    public List<ClienteResponse> buscarTodosClientes() {
        var clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    private void validarCpfParaAtualizacao(ClientePessoaFisicaRequest clienteRequest, Cliente clienteExistente) {
        clienteRepository.findByCpf(clienteRequest.getCpf()).ifPresent(clienteComCnpjExistente -> {
            if (!clienteComCnpjExistente.getId().equals(clienteExistente.getId())) {
                throw new IntegrationException("CPF já cadastrado por outro cliente.");
            }
        });
    }

    private void validarCnpjParaAtualizacao(ClientePessoaJuridicaRequest clienteRequest, Cliente clienteExistente) {
        clienteRepository.findByCnpj(clienteRequest.getCnpj()).ifPresent(clienteComCnpjExistente -> {
            if (!clienteComCnpjExistente.getId().equals(clienteExistente.getId())) {
                throw new IntegrationException("CNPJ já cadastrado por outro cliente.");
            }
        });
    }

    private void validarCnpj(ClientePessoaJuridicaRequest clienteRequest) {
        if (clienteRepository.findByCnpj(clienteRequest.getCnpj()).isPresent()) {
            throw new IntegrationException("CNPJ já cadastrado.");
        }
    }

    private void validarCpf(ClientePessoaFisicaRequest clienteRequest) {
        if (clienteRepository.findByCpf(clienteRequest.getCpf()).isPresent()) {
            throw new IntegrationException("CPF já cadastrado.");
        }
    }
}