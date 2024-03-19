package br.com.elotech.entrevista.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContatoCreateDTO {

    private Integer id;

    @NotNull @NotEmpty
    private Integer pessoaId;

    @NotNull @NotEmpty
    private String nome;

    @NotNull @NotEmpty
    private String telefone;

    @NotNull @NotEmpty
    private String email;

}
