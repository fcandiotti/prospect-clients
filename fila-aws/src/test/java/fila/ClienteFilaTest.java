package fila;

import br.com.desafio.precadastroclientes.cliente.fila.ClienteFila;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteFilaTest {

    private ClienteFila fila;

    @BeforeEach
    void setUp() {
        fila = new ClienteFila(100);
    }

    @Test
    void enfileirar_deveAdicionarClienteNaFila_quandoSolicitado() {
        var cliente = new ClienteResponse();
        fila.enfileirar(cliente);

        assertFalse(fila.isVazia());
        assertTrue(List.of(fila.listaTodos()).contains(cliente));
    }

    @Test
    void desenfileirar_deveRemoverClienteDaFila_quandoSolicitado() {
        var cliente1 = new ClienteResponse();
        var cliente2 = new ClienteResponse();

        fila.enfileirar(cliente1);
        fila.enfileirar(cliente2);

        var clienteDesenfileirado = fila.desenfileirar();

        assertEquals(cliente1, clienteDesenfileirado);
        assertTrue(List.of(fila.listaTodos()).contains(cliente2));
    }

    @Test
    void isVazia_deveVerificarSeFilaEstaVazia_quandoSolicitado() {
        assertTrue(fila.isVazia());

        var cliente = new ClienteResponse();
        fila.enfileirar(cliente);

        assertFalse(fila.isVazia());
    }

    @Test
    void listaTodos_deveRetornarTodosOsDadosNaFila_quandoSolicitado() {
        var cliente1 = new ClienteResponse();
        var cliente2 = new ClienteResponse();

        fila.enfileirar(cliente1);
        fila.enfileirar(cliente2);

        var todosClientes = fila.listaTodos();

        assertEquals(2, todosClientes.length);
        assertEquals(cliente1, todosClientes[0]);
        assertEquals(cliente2, todosClientes[1]);
    }
}