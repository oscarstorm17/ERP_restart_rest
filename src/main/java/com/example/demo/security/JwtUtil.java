package com.example.demo.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final SecretKey secretKey = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey12".getBytes());
	private final long ExpirationTime = 1000*60*60;
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ExpirationTime))
				.signWith(secretKey)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
