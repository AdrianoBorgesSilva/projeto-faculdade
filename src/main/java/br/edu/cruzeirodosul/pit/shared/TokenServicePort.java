package br.edu.cruzeirodosul.pit.shared;

import org.springframework.security.core.Authentication;

public interface TokenServicePort {
    String generateToken(Authentication authentication);
}
