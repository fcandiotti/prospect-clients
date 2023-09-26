package br.com.desafio.precadastroclientes.cliente.controller;

import br.com.desafio.precadastroclientes.cliente.model.dto.*;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")

public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping("/pj")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientePessoaJuridicaResponse criarClientePj(@Valid @RequestBody ClientePessoaJuridicaRequest request) {
        return clienteService.criarClientePj(request);
    }

    @PostMapping("/pf")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientePessoaFisicaResponse criarClientePf(@Valid @RequestBody ClientePessoaFisicaRequest request) {
        return clienteService.criarClientePf(request);
    }

    @PutMapping("/pj/{id}")
    public ClientePessoaJuridicaResponse atualizarClientePj(@PathVariable Long id,
                                                            @RequestBody ClientePessoaJuridicaRequest request) {
        return clienteService.atualizarClientePj(id, request);
    }

    @PutMapping("/pf/{id}")
    public ClientePessoaFisicaResponse atualizarClientePf(@PathVariable Long id,
                                                            @RequestBody ClientePessoaFisicaRequest request) {
        return clienteService.atualizarClientePf(id, request);
    }

    @GetMapping()
    public List<ClienteResponse> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    @DeleteMapping("/{id}")
    public void deleteClienteById(@PathVariable Long id) {
        clienteService.deleteById(id);
    }
}