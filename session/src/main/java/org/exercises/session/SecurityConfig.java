package org.exercises.session;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .successHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_OK))

                        .failureHandler((request, response, exception) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                )

                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_NO_CONTENT)
                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
