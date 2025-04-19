package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Usuario;
import io.github.joaoVitorLeal.exception.SenhaInvalidaException;
import io.github.joaoVitorLeal.rest.dto.CredenciaisRequestDTO;
import io.github.joaoVitorLeal.rest.dto.TokenResponseDTO;
import io.github.joaoVitorLeal.rest.dto.UsuarioRequestDTO;
import io.github.joaoVitorLeal.rest.mapper.UsuarioMapper;
import io.github.joaoVitorLeal.security.jwt.JwtService;
import io.github.joaoVitorLeal.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl service;
    private final UsuarioMapper mapper;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = service.salvar(mapper.converterParaEntidade(dto));
        URI location = URI.create("api/usuarios/" + usuario.getId());
        return ResponseEntity.created(location).body(usuario);
    }

    @PostMapping("/auth")
    public TokenResponseDTO autenticar(@RequestBody @Valid CredenciaisRequestDTO credenciais) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();

            UserDetails usuarioAutenticado = service.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);

            return new TokenResponseDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
