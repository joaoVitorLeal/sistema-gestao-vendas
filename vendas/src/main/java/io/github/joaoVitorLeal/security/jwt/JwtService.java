package io.github.joaoVitorLeal.security.jwt;

import io.github.joaoVitorLeal.VendasApplication;
import io.github.joaoVitorLeal.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Codificar e Decodificar TOKEN
 * */
@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString); // Data hora de expiração do token
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant(); // Transformar o objeto dateTime em Instant
        Date data = Date.from(instant); // Transformar  o Instant em Data

        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) // Parte do payload — colocar os metadados do usuário no payload do Token
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // realizar assinatura — algorítimo de assinatura, chave de assinatura
                .compact(); // retorna o token como String
    }

    // Obter informações do Token
    // Claims -> informações declaradas dentro do payload de um token JWT, como dados do usuário, permissões ou tempos de expiração.
    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser() // decodificador do token
                .setSigningKey(chaveAssinatura) // definir chave de assinatura do token
                .parseClaimsJws(token) // define o token
                .getBody(); // retornar os claims do token.
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterDadosPorLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class); // Inicia a aplicação Spring Boot em modo standalone (de forma indepêndente, sem servidor web) para fins de teste
        JwtService service = contexto.getBean(JwtService.class); // Recupera uma instância do JwtService do contexto do Spring (container de Inversão de Controle)
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("O token está válido?\n " + isTokenValido);

        System.out.println(service.obterDadosPorLoginUsuario(token));
    }
}
