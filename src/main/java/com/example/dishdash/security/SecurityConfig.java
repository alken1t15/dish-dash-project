package com.example.dishdash.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeHttpRequests(authenticated ->{
            authenticated.requestMatchers("").authenticated();
            authenticated.requestMatchers("/profile/").authenticated();
            authenticated.requestMatchers("").authenticated();
            authenticated.anyRequest().permitAll();
        });
        http.formLogin().loginPage("/login").loginProcessingUrl("/process_login").failureUrl("/login?error")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
