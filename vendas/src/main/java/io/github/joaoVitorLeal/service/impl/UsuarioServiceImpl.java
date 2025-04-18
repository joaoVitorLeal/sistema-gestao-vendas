package io.github.joaoVitorLeal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Carregar usuário da base de dados através do login
     * e fornece para o AuthanticationManagerBuilder do SecurityConfiguration.class
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("cicrano")) {
            throw new UsernameNotFoundException("{campo.invalido.username}");
        }

        return User // classe que possui um build que permite que retornemos um UserDetais
                .builder()
                .username("cicrano")
                .password(encoder.encode("123"))
                .roles("USER", "ADMIN")
                .build();
    }
}
