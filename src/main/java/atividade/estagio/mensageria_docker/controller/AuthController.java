package atividade.estagio.mensageria_docker.controller;

import atividade.estagio.mensageria_docker.security.TokenGenerate;
import atividade.estagio.mensageria_docker.security.UsuarioJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//endpoint responsável pelo login.
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private TokenGenerate tokenGenerate;

    // Usuário fixo (mock) pra teste — normalmente seria validado via banco de dados.
    private static final String USER = "admin";
    private static final String PASSWORD = "1234";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioJWT usuario) {
        if (USER.equals(usuario.getUsername()) && PASSWORD.equals(usuario.getPassword())) { //Verifica se as credenciais são válidas.
            String token = tokenGenerate.generateToken(usuario.getUsername()); //Gera o token e devolve em formato JSON:
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
