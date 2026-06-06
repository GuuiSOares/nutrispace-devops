package com.nutrispace.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey secretKey;
	private final long expirationMs;

	public JwtService(
			@Value("${nutrispace.jwt.secret}") String secret,
			@Value("${nutrispace.jwt.expiration-ms:86400000}") long expirationMs) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMs = expirationMs;
	}

	public String gerarToken(String email, Long idAstronauta) {
		Date agora = new Date();
		Date expiracao = new Date(agora.getTime() + expirationMs);
		return Jwts.builder()
				.subject(email)
				.claim("idAstronauta", idAstronauta)
				.issuedAt(agora)
				.expiration(expiracao)
				.signWith(secretKey)
				.compact();
	}

	public String extrairEmail(String token) {
		return extrairClaims(token).getSubject();
	}

	public Long extrairIdAstronauta(String token) {
		return extrairClaims(token).get("idAstronauta", Long.class);
	}

	public boolean tokenValido(String token) {
		try {
			Claims claims = extrairClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception ex) {
			return false;
		}
	}

	private Claims extrairClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
