package br.com.elotech.entrevista.domain.dto;

import jakarta.validation.constraints.Min;
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
public class ContatoUpdateDTO {

    @NotNull @Min(1)
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
