package br.com.desafio.precadastroclientes.cliente.model;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaFisicaRequest;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClientePessoaJuridicaRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;


@Entity
@Table(name = "cliente")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ETipoCliente tipoCliente;

    @Column(length = 14)
    private String cnpj;

    @Column(length = 50)
    private String razaoSocial;

    @Column(length = 4, nullable = false)
    private String mcc;

    @Column(length = 11)
    private String cpf;

    @Column(length = 50)
    private String nome;

    @Column
    private String email;

    @Column(length = 11)
    private String cpfContato;

    @Column(length = 50)
    private String nomeContato;

    @Column
    private String emailContato;

    public static Cliente of(ClientePessoaFisicaRequest request) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public static Cliente of(ClientePessoaJuridicaRequest request) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public void atualizarClientePj(ClientePessoaJuridicaRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    public void atualizarClientePf(ClientePessoaFisicaRequest request) {
        BeanUtils.copyProperties(request, this);
    }
}