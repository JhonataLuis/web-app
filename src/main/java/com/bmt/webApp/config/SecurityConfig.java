package com.bmt.webApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configurações de segurança, como autenticação e autorização
        http.csrf().disable() // Desabilita CSRF para simplificação, mas deve ser usado com cautela
            .authorizeRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas as requisições, ajuste conforme necessário
                )
            .formLogin().disable() // Desabilita o login via formulário, se necessário
            .httpBasic().disable();// Usa autenticação básica HTTP
           
        return http.build();
    }
}
