package com.codecraft.Crud_app.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("person".equals(username)) {
            return User.withUsername("person")
                    .password("{noop}personpassword")
                    .roles("PERSON")
                    .build();
        } else if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}adminpassword")
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}