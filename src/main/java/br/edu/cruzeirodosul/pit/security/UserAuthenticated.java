package br.edu.cruzeirodosul.pit.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.cruzeirodosul.pit.shared.UserAuthenticationDTO;

public class UserAuthenticated implements UserDetails{

    private final UserAuthenticationDTO user;

    public UserAuthenticated(UserAuthenticationDTO user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override // Username == Email
    public String getUsername() {
        return user.getEmail();
    }

    public String getId() {
        return user.getId();
    }
    
}
