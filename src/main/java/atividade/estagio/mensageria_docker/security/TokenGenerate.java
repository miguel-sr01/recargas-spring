

package atividade.estagio.mensageria_docker.security;


import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenGenerate {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String jwtSecret() {
        return dotenv.get("JWT_SECRET");
    }

    @Bean
    public long jwtExpiration() {
        return Long.parseLong(dotenv.get("JWT_EXPIRATION"));
    }

    //Colocar em um .env futuramente
    private final String SECRET_KEY = jwtSecret();// mínimo 32 caracteres pro algoritmo HMAC funcionar

    private final long EXPIRATION_TIME = jwtExpiration(); // 1 hora em milissegundos

    //Constrói uma chave Key a partir da SECRET_KEY.
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Começa a construção do token JWT.
    public String generateToken(String username) {
        System.out.println("aquiii");
        System.out.println(SECRET_KEY);
        System.out.println(EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(username) //quem o token representa (ex: “admin”)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Marca o horário em que o token foi emitido.
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //Marca o horário em que o token expira (atual + 1 hora, no caso).
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //Assina o token com a chave secreta e o algoritmo HS256 (HMAC-SHA256).
                .compact(); //Finaliza o token e o retorna como uma string (exemplo: "eyJhbGciOiJIUzI1NiJ9...").
    }

    // Metodo para ler o nome do usuario de dentro do token
    public String extractUsername(String token) {
        return Jwts.parserBuilder() //Valida a assinatura com SECRET_KEY
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se o token é válido e não expirou
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}