package br.edu.cruzeirodosul.pit.movie;

import org.springframework.stereotype.Component;

@Component
public class MovieEventListener {
    
    private final MovieService movieService;

    MovieEventListener(MovieService movieService) {
        this.movieService = movieService;
    }
}
