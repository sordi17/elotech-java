package br.com.elotech.entrevista.domain.dto;

import br.com.elotech.entrevista.domain.Contato;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PessoaCreateDTO {

    private Integer id;

    @NotNull @NotEmpty
    private String nome;

    @NotNull @NotEmpty
    private String cpf;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    @NotNull @NotEmpty
    private List<Contato> contatos;

}
