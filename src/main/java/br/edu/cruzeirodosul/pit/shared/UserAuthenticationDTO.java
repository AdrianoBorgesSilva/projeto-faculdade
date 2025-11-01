package br.edu.cruzeirodosul.pit.shared;

import java.io.Serializable;

public class UserAuthenticationDTO implements Serializable{
    
    private static final long serialVersionUID = 1L; 

    private final String id;
    private final String name;
    private final String email;
    private final String password;

    public UserAuthenticationDTO(String id, String name, String email, String password) {
        this.id = id;
        this.name = name; 
        this.email = email;
        this.password = password; 
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
