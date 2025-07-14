package com.jb.common_template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jb.common_template.security.JwtAuthenticationEntryPoint;
import com.jb.common_template.security.JwtAuthenticationFilter;


@Configuration

@EnableMethodSecurity

public class SpringSecurityConfig 
{

    // private final UserDetailsService userDetailsService;

    // public SpringSecurityConfig(UserDetailsService userDetailsService) 
    //{
    //     this.userDetailsService = userDetailsService;
    // }

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtauthenticationFilter;

    public SpringSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAuthenticationFilter jwtauthenticationFilter) 
    {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtauthenticationFilter = jwtauthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception 
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> 
            {
                // authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
                // authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();

                authorize.requestMatchers("/api/auth/**").permitAll(); // login & register
                authorize.anyRequest().authenticated();
                
                
            })
            .httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(jwtauthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}