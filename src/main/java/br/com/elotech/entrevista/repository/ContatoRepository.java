package br.com.elotech.entrevista.repository;

import br.com.elotech.entrevista.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    List<Contato> findAllByPessoaId(Integer pessoaId);
}
