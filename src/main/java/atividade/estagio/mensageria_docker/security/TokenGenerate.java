package atividade.estagio.mensageria_docker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenGenerate {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    //Constrói uma chave Key a partir da SECRET_KEY.
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Começa a construção do token JWT.
    public String generateToken(String username) {
        System.out.println("Testando Token JWT");
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