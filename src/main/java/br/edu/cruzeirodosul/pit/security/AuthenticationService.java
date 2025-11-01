package br.edu.cruzeirodosul.pit.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.cruzeirodosul.pit.shared.TokenServicePort;

@Service
public class AuthenticationService implements TokenServicePort{
    
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService){
        this.jwtService = jwtService;
    }

    /*public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }*/

    @Override
    public String generateToken(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
