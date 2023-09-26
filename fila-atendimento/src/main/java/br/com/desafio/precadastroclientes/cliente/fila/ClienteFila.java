package br.com.desafio.precadastroclientes.cliente.fila;

import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;

public class ClienteFila {
    private ClienteResponse[] clientes;
    private int tamanho, frente, fim;

    public ClienteFila(int capacidade) {
        clientes = new ClienteResponse[capacidade];
        frente = 0;
        fim = -1;
        tamanho = 0;
    }

    public void enfileirar(ClienteResponse clienteResponse) {
        if(fim == clientes.length - 1) {
            fim = -1;
        }
        clientes[++fim] = clienteResponse;
        tamanho++;
    }

    public ClienteResponse desenfileirar() {
        var cliente = clientes[frente++];
        if(frente == clientes.length) {
            frente = 0;
        }
        tamanho--;
        return cliente;
    }

    public boolean isVazia() {
        return tamanho == 0;
    }

    public ClienteResponse[] listaTodos() {
        ClienteResponse[] clienteArray = new ClienteResponse[tamanho];
        for (int i = 0; i < tamanho; i++) {
            clienteArray[i] = clientes[(frente + i) % clientes.length];
        }
        return clienteArray;
    }
}