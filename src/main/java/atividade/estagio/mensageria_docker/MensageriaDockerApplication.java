package atividade.estagio.mensageria_docker;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@EnableRabbit
@EnableScheduling
@SpringBootApplication
public class MensageriaDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MensageriaDockerApplication.class, args);
	}

}
