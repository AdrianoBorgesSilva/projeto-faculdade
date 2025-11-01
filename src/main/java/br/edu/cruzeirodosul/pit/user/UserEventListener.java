package br.edu.cruzeirodosul.pit.user;

import org.springframework.stereotype.Component;


@Component
public class UserEventListener {
    
    private final UserService userService;

    public UserEventListener(UserService userService) {
        this.userService = userService;
    }
}
