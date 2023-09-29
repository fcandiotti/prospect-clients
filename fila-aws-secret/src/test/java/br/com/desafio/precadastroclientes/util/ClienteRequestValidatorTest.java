package br.com.desafio.precadastroclientes.util;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import br.com.desafio.precadastroclientes.cliente.util.ClienteRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClienteRequestValidatorTest {

    private ClienteRequestValidator validator;
    private ClienteRequest request;
    private BindException errors;

    @BeforeEach
    public void setup() {
        validator = new ClienteRequestValidator();
        request = new ClienteRequest();
        errors = new BindException(request, "clienteRequest");
    }


    @Test
    public void pessoaFisicaValida_deveAceitarRequest_quandoDadosObrigatoriosPreenchidos() {
        request.setMcc("1234");
        request.setTipoCliente(ETipoCliente.PESSOA_FISICA);
        request.setCpfPessoa("12345678901");
        request.setNomePessoa("Nome Valido");
        request.setEmailPessoa("teste@valido.com");

        validator.validate(request, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void pessoaJuridicaValida_deveAceitarRequest_quandoDadosObrigatoriosPreenchidos() {
        request.setTipoCliente(ETipoCliente.PESSOA_JURIDICA);
        request.setCnpj("12345678901234");
        request.setRazaoSocial("Razão Social Válida");
        request.setMcc("1234");
        request.setCpfContato("12345678901");
        request.setNomeContato("Nome Contato");
        request.setEmailContato("contato@valido.com");


        validator.validate(request, errors);
        assertFalse(errors.hasErrors());
    }
}