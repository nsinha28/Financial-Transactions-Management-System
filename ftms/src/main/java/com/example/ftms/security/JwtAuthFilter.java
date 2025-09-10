package com.example.ftms.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ftms.model.UserEntity;
import com.example.ftms.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader= request.getHeader("Auhorization");
		String token= null;
		String username= null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			token= authHeader.substring(7);
			username= jwtUtil.extarctUsername(token);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserEntity user= userRepository.findByUsername(username);
			if(user!=null && jwtUtil.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(username, null, null);
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
