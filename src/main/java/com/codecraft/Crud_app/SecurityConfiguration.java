package com.codecraft.Crud_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository())
                        .requireCsrfProtectionMatcher(new RequestMatcher() {
                            @Override
                            public boolean matches(jakarta.servlet.http.HttpServletRequest request) {
                                return false;
                            }

                            public boolean matches(HttpServletRequest request) {
                                return !request.getRequestURI().equals("/api/tasks") || !request.isUserInRole("ADMIN");
                            }
                        })
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/api/tasks").hasAnyRole("ADMIN", "PERSON")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminpassword")
                .roles("ADMIN")
                .build();

        UserDetails person = User.withDefaultPasswordEncoder()
                .username("person")
                .password("personpassword")
                .roles("PERSON")
                .build();

        return new InMemoryUserDetailsManager(admin, person);
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
}