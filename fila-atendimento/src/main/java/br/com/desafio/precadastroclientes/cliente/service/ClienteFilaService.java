package br.com.desafio.precadastroclientes.cliente.service;

import br.com.desafio.precadastroclientes.cliente.fila.ClienteFila;
import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import org.springframework.stereotype.Service;

@Service
public class ClienteFilaService {
    private final ClienteFila filaDeClientes = new ClienteFila(100);

    public void enfileirar(ClienteResponse clienteResponse) {
        filaDeClientes.enfileirar(clienteResponse);
    }

    public ClienteResponse chamarProximoCliente() {
        if (filaDeClientes.isVazia()) {
            return null;
        }
        return filaDeClientes.desenfileirar();
    }

    public boolean isVazia() {
        return filaDeClientes.isVazia();
    }

    public ClienteResponse[] todos() {
        return filaDeClientes.listaTodos();
    }

    public void atualizarClienteNaFila(Cliente clienteAtualizado) {
        filaDeClientes.desenfileirar();
        filaDeClientes.enfileirar(ClienteResponse.of(clienteAtualizado));
    }
}