package br.com.desafio.precadastroclientes.cliente.controller;

import br.com.desafio.precadastroclientes.cliente.service.ClienteFilaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fila")
public class ClienteFilaController {

    @Autowired
    private ClienteFilaService filaService;

    @GetMapping("/proximo")
    public ResponseEntity<?> proximoCliente() {
        var cliente = filaService.chamarProximoCliente();
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A fila de atendimento está vazia.");
        }
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/todos")
    public ResponseEntity<?> todosClientes() {
        if (filaService.isVazia()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("A fila de atendimento está vazia.");
        }
        return ResponseEntity.ok().body(filaService.todos());
    }
}

