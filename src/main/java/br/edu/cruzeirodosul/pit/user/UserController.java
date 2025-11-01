package br.edu.cruzeirodosul.pit.user;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.cruzeirodosul.pit.shared.TokenServicePort;
import br.edu.cruzeirodosul.pit.user.dto.LoginResponseDTO;
import br.edu.cruzeirodosul.pit.user.dto.UserDTO;
import br.edu.cruzeirodosul.pit.user.dto.UserLoginDTO;
import br.edu.cruzeirodosul.pit.user.dto.UserSignUpDTO;
import br.edu.cruzeirodosul.pit.user.dto.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Controller responsável pelas ações relacionadas ao usuário.")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    
    private final UserService userService;
    private final TokenServicePort tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, TokenServicePort tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários registrados no banco de dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<List<UserDTO>> findAllUsers(Authentication authentication) {
        List<UserDTO> users = userService.findAllUsers().stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obter um único usuário.", description = "Retorna o usuário correspondente ao ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String userId, Authentication authentication){
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @Operation(summary = "Criar um usuário", description = "Cria um usuário padrão no banco de dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro de validação."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        
        User user = userService.createUser(userSignUpDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
       
        return ResponseEntity.created(uri).build();
    }
    
    @Operation(summary = "Login do usuário.", description = "Login a partir de email e senha.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário logado, token gerado com sucesso."),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas (email ou senha incorretos)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLogin) {
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
      
        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Atualizar usuário.", description = "Atualização de um usuário existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
        @ApiResponse(responseCode = "401", description = "Não autenticado (token inválido ou em falta)."),
        @ApiResponse(responseCode = "403", description = "Proibido (sem permissão para acessar esse recurso)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable String userId, Authentication authentication) {
        
        userService.updateUser(userUpdateDTO, userId, authentication.getName());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletar um usuário.", description = "Deleção de um usuário existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
        @ApiResponse(responseCode = "401", description = "Não autenticado (token inválido ou em falta)."),
        @ApiResponse(responseCode = "403", description = "Proibido (sem permissão para acessar esse recurso)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, Authentication authentication) {

        userService.deleteUser(userId, authentication.getName());

        return ResponseEntity.ok().build();
    }
}
