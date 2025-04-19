package io.github.joaoVitorLeal.rest.mapper;

import io.github.joaoVitorLeal.domain.entity.Usuario;
import io.github.joaoVitorLeal.rest.dto.UsuarioRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario converterParaEntidade(UsuarioRequestDTO dto) {
         return Usuario.builder()
                 .login(dto.getLogin())
                 .senha(dto.getSenha())
                 .admin(dto.isAdmin())
                 .build();
    }
}
