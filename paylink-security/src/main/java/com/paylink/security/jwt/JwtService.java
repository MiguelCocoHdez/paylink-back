package com.paylink.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	private final SecretKey secretKey;
	
	public JwtService(String secret) { //La secret key se hace desde el constructor para que cada micro tenga la suya
        byte[] bytes = Base64.getDecoder().decode(secret);
        this.secretKey = new SecretKeySpec(bytes, "HmacSHA256");
    }
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(secretKey)
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
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
