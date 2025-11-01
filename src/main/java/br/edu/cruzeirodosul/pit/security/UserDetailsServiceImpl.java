package br.edu.cruzeirodosul.pit.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.cruzeirodosul.pit.shared.UserAuthenticationDTO;
import br.edu.cruzeirodosul.pit.shared.UserAuthenticationPort;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserAuthenticationPort userAuthenticationPort;

    public UserDetailsServiceImpl(UserAuthenticationPort userAuthenticationPort) {
        this.userAuthenticationPort = userAuthenticationPort;
    }

    @Override // Username == Email
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        UserAuthenticationDTO account = userAuthenticationPort.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found" + email));
        
        return new UserAuthenticated(account); 
    }
    
}
