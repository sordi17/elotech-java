package br.com.elotech.entrevista.repository;

import br.com.elotech.entrevista.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query(value = "select p from Pessoa p where p.nome like %:filtro%")
    Page<Pessoa> findAllWithFilter(Pageable pageable, @Param("filtro") String filtro);
}
