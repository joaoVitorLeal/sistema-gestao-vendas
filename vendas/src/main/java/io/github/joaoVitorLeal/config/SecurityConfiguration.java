package io.github.joaoVitorLeal.config;

import io.github.joaoVitorLeal.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    /**
     * Criptografar e descriptografar senhas de usuário.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * (AUTENTICAÇÃO)
     * Fornecer os objetos que realiza a autenticação dos usuários
     * e adicionar esses usuarios ao contexto do Security.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * (AUTORIZAÇÃO) Obtém o usuário autenticado e verifica se possui autorização
     * para fazer uma determinada operação no sistema.
     * Configura a autorização de URL's.
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Configuração que permite a segurança entre um backend e uma aplicação web.
                .authorizeRequests()
                    .antMatchers("/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("ADMIN", "USER")
                    .antMatchers("/api/produtos/**")
                        .hasRole("ADMIN")
                .and() // Retorna para raiz do objeto 'http'.
                    .httpBasic();
    }
}
