package br.com.desafio.precadastroclientes.cliente.model;

import br.com.desafio.precadastroclientes.cliente.enums.ETipoCliente;
import br.com.desafio.precadastroclientes.cliente.model.dto.ClienteRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Pattern;
import java.util.UUID;

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
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();
    @Enumerated(EnumType.STRING)
    private ETipoCliente tipoCliente;
    @Column(length = 4, nullable = false)
    private String mcc;
    @Column(length = 14)
    private String cnpj;
    @Column(length = 50)
    private String razaoSocial;
    @Column(length = 11)
    private String cpfContato;
    @Column(length = 50)
    private String nomeContato;
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String emailContato;
    @Column(length = 11)
    private String cpfPessoa;
    @Column(length = 50)
    private String nomePessoa;
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String emailPessoa;

    public static Cliente of(ClienteRequest request) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(request, cliente);
        return cliente;
    }

    public void atualizarCliente(ClienteRequest request) {
        BeanUtils.copyProperties(request, this);
    }
}