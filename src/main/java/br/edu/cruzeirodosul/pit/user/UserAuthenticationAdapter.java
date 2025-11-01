package br.edu.cruzeirodosul.pit.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.cruzeirodosul.pit.shared.UserAuthenticationDTO;
import br.edu.cruzeirodosul.pit.shared.UserAuthenticationPort;

@Service
public class UserAuthenticationAdapter implements UserAuthenticationPort{

    private final UserRepository userRepository;

    public UserAuthenticationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserAuthenticationDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> new UserAuthenticationDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword()));
    }
    
}
