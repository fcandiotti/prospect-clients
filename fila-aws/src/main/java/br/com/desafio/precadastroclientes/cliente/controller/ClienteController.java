package br.com.desafio.precadastroclientes.cliente.controller;

import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import br.com.desafio.precadastroclientes.cliente.service.ClienteService;
import br.com.desafio.precadastroclientes.cliente.util.ClienteRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRequestValidator clienteRequestValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(clienteRequestValidator);
    }

    @GetMapping
    public List<ClienteResponse> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criarCliente(@Valid @RequestBody ClienteRequest request) {
        return clienteService.criarCliente(request);
    }

    @PutMapping("{id}")
    public ClienteResponse atualizarClientePj(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return clienteService.atualizarCliente(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteClienteById(@PathVariable Long id) {
        clienteService.deleteById(id);
    }
}