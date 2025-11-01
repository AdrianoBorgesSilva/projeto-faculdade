package br.edu.cruzeirodosul.pit.shared;

import java.util.Optional;

public interface UserAuthenticationPort {
    Optional<UserAuthenticationDTO> findByEmail(String email);
}
