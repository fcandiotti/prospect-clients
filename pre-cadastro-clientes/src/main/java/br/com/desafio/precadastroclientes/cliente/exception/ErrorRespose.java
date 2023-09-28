package br.com.desafio.precadastroclientes.cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRespose {

    private int status;
    private String message;
}
