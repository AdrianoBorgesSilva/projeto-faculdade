package br.edu.cruzeirodosul.pit.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.cruzeirodosul.pit.exception.ObjectNotFoundException;
import br.edu.cruzeirodosul.pit.exception.SignUpException;
import br.edu.cruzeirodosul.pit.user.dto.UserSignUpDTO;
import br.edu.cruzeirodosul.pit.user.dto.UserUpdateDTO;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public User createUser(UserSignUpDTO userSignUpDTO) {
        
        if (userRepository.existsByEmail(userSignUpDTO.getEmail())) {
            throw new SignUpException("Email already in use");
        }
        
        String encryptedPassword = passwordEncoder.encode(userSignUpDTO.getPassword());

        User user = new User(null, userSignUpDTO.getName(), userSignUpDTO.getEmail(), encryptedPassword);
    
        return userRepository.save(user);
    }

    public void updateUser(UserUpdateDTO userUpdateDTO, String userId, String authEmail) {
        User user = findUserById(userId);
        userRepository.save(user);
    }

    public void deleteUser(String userId,  String authEmail) {  
        userRepository.deleteById(userId);
    }
}
