package io.github.joaoVitorLeal.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {
    @NotBlank(message = "{campo.obrigatorio.login}")
    private String login;

    @NotBlank(message = "{campo.obrigatorio.senha}")
    private String senha;

    private boolean admin;
}