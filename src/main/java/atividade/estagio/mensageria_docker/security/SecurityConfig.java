package atividade.estagio.mensageria_docker.security;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//configura a segurança da aplicação — define o que precisa de login,
//e valida o token em cada requisição.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private TokenGenerate tokenGenerate;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().hasAnyAuthority("ROLE_USER")
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    FilterChain chain)
                            throws ServletException, IOException {

                        String authHeader = request.getHeader("Authorization");
                        if (authHeader != null && authHeader.startsWith("Bearer ")) {
                            String token = authHeader.substring(7);
                            if (tokenGenerate.isTokenValid(token)) {
                                System.out.println("Token válido para: " + tokenGenerate.extractUsername(token));
                                var auth = new UsernamePasswordAuthenticationToken(
                                        tokenGenerate.extractUsername(token),
                                        null,
                                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                                );
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            } else {
                                System.out.println("Token inválido ou expirado!");
                            }
                        } else {
                            System.out.println("Nenhum header Authorization encontrado!");
                        }

                        chain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);

        // Permitir acesso ao H2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

}
