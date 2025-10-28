package com.paylink.auth.infrastructure.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey())
				.compact();			
	}
	
	public String generateToken(UserDetails userDetails) { //usar este para añadirlo sin claims extra
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractAllClaims(token).getSubject();
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	private SecretKey getSignInKey() {
		byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
		
		return new SecretKeySpec(bytes, "HmacSHA256"); 
	}
}
