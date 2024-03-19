package br.com.elotech.entrevista.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PESSOA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cpf;

    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")
    private List<Contato> contatos;

}
