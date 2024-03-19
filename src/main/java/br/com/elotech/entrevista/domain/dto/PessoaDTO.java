package br.com.elotech.entrevista.domain.dto;

import br.com.elotech.entrevista.domain.Contato;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PessoaDTO {

    private Integer id;
    private String nome;
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    private List<Contato> contatos;
}
