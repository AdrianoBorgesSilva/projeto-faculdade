package br.edu.cruzeirodosul.pit.movie;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
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

import br.edu.cruzeirodosul.pit.movie.dto.CreateMovieDTO;
import br.edu.cruzeirodosul.pit.movie.dto.MovieDTO;
import br.edu.cruzeirodosul.pit.movie.dto.UpdateMovieDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Controller responsável pelas ações relacionadas aos filmes.")
@SecurityRequirement(name = "Bearer Authentication")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os filmes.", description = "Retorna uma lista com todos os filmes registrados no banco de dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filmes retornados com sucesso."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<List<MovieDTO>> findAllMovies(Authentication authentication) {
        
        List<MovieDTO> movie = movieService.findAllMovies().stream().map(MovieDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Obter um único filme.", description = "Retorna o filme correspondente ao ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable String movieId, Authentication authentication){
        Movie movie = movieService.findMovieById(movieId);
        return ResponseEntity.ok(new MovieDTO(movie));
    }

    @PostMapping
    @Operation(summary = "Criar um filme.", description = "Cria um novo filme no banco de dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Filme criado com sucesso."),
        @ApiResponse(responseCode = "401", description = "Não autenticado (token inválido ou em falta)."),
        @ApiResponse(responseCode = "403", description = "Proibido (sem permissão para acessar esse recurso)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<Void> createMovies(@RequestBody CreateMovieDTO movieDTO, Authentication authentication) {

        Movie movie = movieService.createMovie(movieDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{movieId}")
    @Operation(summary = "Atualizar um filme.", description = "Atualizar um filme existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado (token inválido ou em falta)."),
        @ApiResponse(responseCode = "403", description = "Proibido (sem permissão para acessar esse recurso)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<Void> updateMovie(@RequestBody UpdateMovieDTO movieDTO, @PathVariable String movieId, Authentication authentication) {
        movieService.updateMovie(movieDTO, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    @Operation(summary = "Deteletar um filme.", description = "Deletar um filme existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filme deletado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado (token inválido ou em falta)."),
        @ApiResponse(responseCode = "403", description = "Proibido (sem permissão para acessar esse recurso)."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId, Authentication authentication) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok().build();
    }
}
