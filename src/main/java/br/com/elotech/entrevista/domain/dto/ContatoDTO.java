package br.com.elotech.entrevista.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContatoDTO {

    private Integer id;
    private Integer pessoaId;
    private String nome;
    private String telefone;
    private String email;

}
