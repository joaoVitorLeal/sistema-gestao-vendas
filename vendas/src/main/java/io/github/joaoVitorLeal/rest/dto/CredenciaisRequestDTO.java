package io.github.joaoVitorLeal.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CredenciaisRequestDTO {
    @NotBlank(message = "{campo.obrigatorio.login}")
    private String login;

    @NotBlank(message = "{campo.obrigatorio.senha}")
    private String senha;

}
