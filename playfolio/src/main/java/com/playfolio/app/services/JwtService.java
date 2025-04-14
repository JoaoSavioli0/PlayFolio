package com.playfolio.app.services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Chave secreta forte (Base64)
    private static final String SECRET_KEY_BASE64 = "AHE1qwx31ASD31dq490SHDAh2ojoDAiodhaO6JDHAodhaO412DHAO3dhao2DH";

    // Tempo de expiração do token (2 horas)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Obtém a chave de assinatura correta
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY_BASE64));
    }

    // Gera um novo token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // Valida o token e retorna o usuário
    public String validateTokenAndGetUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject(); // Retorna o email armazenado no token

        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}