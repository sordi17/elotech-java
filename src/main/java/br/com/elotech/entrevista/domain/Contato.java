package br.com.elotech.entrevista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CONTATO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pessoa_id")
    private Integer pessoaId;

    private String nome;
    private String telefone;
    private String email;

}
