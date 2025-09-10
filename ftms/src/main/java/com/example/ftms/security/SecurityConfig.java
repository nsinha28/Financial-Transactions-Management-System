package com.example.ftms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	 private final JwtAuthFilter jwtAuthFilter;

	    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
	        this.jwtAuthFilter = jwtAuthFilter;
	    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf(csrf-> csrf.disable()).//disable csrf for all apis
		authorizeHttpRequests(auth-> auth.requestMatchers("/api/auth/**").permitAll().//allow all /api requests
		anyRequest().authenticated());
		
		  httpSecurity.sessionManagement(session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		  
		  // Add JWT filter before Spring's UsernamePasswordAuthenticationFilter
		  httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

}
