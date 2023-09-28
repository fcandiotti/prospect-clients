package br.com.desafio.precadastroclientes.cliente.util;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClienteRequestValidator implements Validator {

    private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";


    @Override
    public boolean supports(Class<?> clazz) {
        return ClienteRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClienteRequest request = (ClienteRequest) target;

        if (request.getTipoCliente() == null) {
            errors.rejectValue("tipoCliente", "TIPOCLIENTEContatoInvalido",
                    "O TIPO DO CLIENTE é obrigatório.");
        }

        if (request.getMcc() == null || request.getMcc().length() > 4) {
            errors.rejectValue("mcc", "MCCContatoInvalido",
                    "O MCC do Contato é obrigatório para o tipo PJ e deve ter 4 dígitos.");
        }

        if (request.getTipoCliente() == ETipoCliente.PESSOA_JURIDICA) {
            if (request.getCnpj() == null || request.getCnpj().length() != 14) {
                errors.rejectValue("cnpj", "CNPJInvalido", "O CNPJ é obrigatório para o tipo PJ e deve ter 14 dígitos.");
            }

            if (request.getRazaoSocial() == null || request.getRazaoSocial().isEmpty() || request.getRazaoSocial().length() > 50) {
                errors.rejectValue("razaoSocial", "RazaoSocialInvalida",
                        "A Razão Social é obrigatória para o tipo PJ e deve ter no máximo 50 caracteres.");
            }

            if (request.getCpfContato() == null || request.getCpfContato().length() != 11) {
                errors.rejectValue("cpfContato", "CPFContatoInvalido",
                        "O CPF do Contato é obrigatório para o tipo PJ e deve ter 11 dígitos.");
            }

            if (request.getNomeContato() == null || request.getNomeContato().isEmpty() || request.getNomeContato().length() > 50) {
                errors.rejectValue("nomeContato", "NomeContatoInvalido",
                        "O Nome do Contato é obrigatório para o tipo PJ e deve ter no máximo 50 caracteres.");
            }

            if (request.getEmailContato() == null || request.getEmailContato().isEmpty() || !request.getEmailContato().matches(EMAIL_REGEX)) {
                errors.rejectValue("emailContato", "EmailContatoInvalido",
                        "O Email do Contato é obrigatório para o tipo PJ e deve estar em um formato válido.");
            }

            if (request.getTipoCliente() == ETipoCliente.PESSOA_FISICA) {
                if (request.getCpfPessoa() == null || request.getCpfPessoa().length() != 11) {
                    errors.rejectValue("cpfPessoa", "CPFPessoaInvalido",
                            "O CPF é obrigatório para o tipo PF e deve ter 11 dígitos.");
                }

                if (request.getNomePessoa() == null || request.getNomePessoa().isEmpty() || request.getNomePessoa().length() > 50) {
                    errors.rejectValue("nomePessoa", "NomePessoaInvalido",
                            "O Nome da Pessoa é obrigatório para o tipo PF e deve ter no máximo 50 caracteres.");
                }

                if (request.getEmailPessoa() == null || request.getEmailPessoa().isEmpty() || !request.getEmailPessoa().matches(EMAIL_REGEX)) {
                    errors.rejectValue("emailPessoa", "EmailPessoaInvalido",
                            "O Email da Pessoa é obrigatório para o tipo PF e deve estar em um formato válido.");
                }
            }
        }
    }
}
