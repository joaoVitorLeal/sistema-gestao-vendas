package io.github.joaoVitorLeal.security.jwt;

import io.github.joaoVitorLeal.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interceptar as requisições, obter o Token do HEADER Authorization
 * e carregar usuário do token para o contexto do Spring Security.
 * */
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    // obter token da requisição — pela chave Authorization do HEADER da requisição
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain ) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) { // Se ele não está nulo e possui 'Bearer' como prefíxo.
            // Dividir o prefíxo 'Bearer' do hash do token, transformando-os em um array contendo esses dois elementos.
            // Em seguida obtém somente o hash que está na posição [1] do array.
            String token = authorization.split(" ")[1];

            boolean isValid = jwtService.tokenValido(token);

            if (isValid) {
                String loginUsuario = jwtService.obterDadosPorLoginUsuario(token);
                UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
                /*
                 * Informa ao contexto do Spring Security que se trata de uma
                 * autenticação de uma aplicação Web.
                 * */
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(user); // Define o usuário autenticado para dentro do contexto, sessão do Spring Security
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        // Prossegue para o configurer() do SecurityConfiguration.class da aplicação
    }
}
