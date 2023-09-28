package br.com.desafio.precadastroclientes.cliente.repository;

import br.com.desafio.precadastroclientes.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpfPessoa(String cpf);
    Optional<Cliente> findByCnpj(String cpf);
    boolean existsByCnpj(String cnpj);
    boolean existsByCpfPessoa(String cpf);
}