package br.com.desafio.precadastroclientes.cliente.controller;

import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteResponse;
import br.com.desafio.precadastroclientes.cliente.service.SqsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fila-clientes")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;
    private final ObjectMapper objectMapper;

    @GetMapping("/consume-sqs")
    public List<ClienteResponse> consumeMessagesFromSqs() {
        var response = sqsService.receiveMessages();
        var sqsMessages = response.messages();
        return sqsMessages.stream()
                .map(msg -> deserializeMessageBodyToDto(msg.body()))
                .collect(Collectors.toList());
    }

    private ClienteResponse deserializeMessageBodyToDto(String messageBody) {
        try {
            return objectMapper.readValue(messageBody, ClienteResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
