package com.ubbcluj.authentication.dto;

import java.util.List;

/**
 * Represents a JWKS (JSON Web Key Set) response containing public keys.
 *
 * @param keys List of JWK response objects
 */
public record JwkSetResponseDto(List<JwkResponseDto> keys) {

    /**
     * Represents a single JSON Web Key (JWK) response.
     *
     * @param kty Key type (e.g., RSA)
     * @param n Modulus (base64-encoded)
     * @param e Exponent (base64-encoded)
     * @param alg Algorithm used for signing (e.g., RS512)
     * @param use Key usage (e.g., sig for signing)
     */
    public record JwkResponseDto(String kty, String n, String e, String alg, String use) { }
}