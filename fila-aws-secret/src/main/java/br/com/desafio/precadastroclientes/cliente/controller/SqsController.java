package br.com.desafio.precadastroclientes.cliente.controller;

import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import br.com.desafio.precadastroclientes.cliente.service.SqsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/fila-clientes")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;
    private final ObjectMapper objectMapper;

    @GetMapping("/consume-sqs")
    public ClienteResponse consumeMessagesFromSqs() {
        var response = sqsService.receiveMessages();

        if (response.messages().isEmpty()) {
            throw new EntityNotFoundException("Nenhuma mensagem disponível na fila.");
        }

        var msg = response.messages().get(0);
        var clienteResponse = deserializeMessageBodyToDto(msg.body());
        log.info("Mensagem com ReceiptHandle {} foi consumida.", msg.receiptHandle());

        sqsService.deleteMessage(msg);

        return clienteResponse;
    }

    private ClienteResponse deserializeMessageBodyToDto(String messageBody) {
        try {
            return objectMapper.readValue(messageBody, ClienteResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
