package io.github.joaoVitorLeal.service.impl;

import io.github.joaoVitorLeal.domain.entity.Usuario;
import io.github.joaoVitorLeal.domain.repository.UsuarioRepository;
import io.github.joaoVitorLeal.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails usuarioEncontrado = loadUserByUsername(usuario.getLogin());
        boolean matches = encoder.matches(usuario.getSenha(), usuarioEncontrado.getPassword());// senha informada, senha (criptografada) cadastrada no db
        if (matches) {
            return usuarioEncontrado;
        }
        throw new SenhaInvalidaException();
    }


    /**
     * Carregar usuário da base de dados através do login
     * e fornece para o AuthanticationManagerBuilder do SecurityConfiguration.class
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username).orElseThrow( () -> new UsernameNotFoundException("Nome de usuário incorreto.") );

        String[] roles = usuario.isAdmin() ? new String[]{"USER", "ADMIN"} : new String[]{"USER"};

        return User // classe que possui um build que permite que retornemos um UserDetais
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
