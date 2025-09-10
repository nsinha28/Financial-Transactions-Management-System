package com.example.ftms.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private final String SECRET= "mysecretkeymysecretkeymysecretkey123";//use atleast 32 characters
	private final long EXPIRATION= 1000 * 60 * 60 ;//1hour
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extarctUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}catch(JwtException e) {
			return false;
		}
	}
}
