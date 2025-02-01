package com.ubbcluj.authentication.service;

import com.ubbcluj.authentication.dto.JwkSetResponseDto;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {


    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwkSetResponseDto getJwks() {
        JwkSetResponseDto.JwkResponseDto responseDTO = new JwkSetResponseDto.JwkResponseDto(
                publicKey.getAlgorithm(),
                Base64.getUrlEncoder().encodeToString(publicKey.getModulus().toByteArray()),
                Base64.getUrlEncoder().encodeToString(publicKey.getPublicExponent().toByteArray()),
                "RS512",
                "sig");
        return new JwkSetResponseDto(List.of(responseDTO));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuer("http://localhost:8080")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(privateKey, Jwts.SIG.RS512)
                .compact();
    }

}
