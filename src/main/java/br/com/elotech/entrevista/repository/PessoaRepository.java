package br.com.elotech.entrevista.repository;

import br.com.elotech.entrevista.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}
