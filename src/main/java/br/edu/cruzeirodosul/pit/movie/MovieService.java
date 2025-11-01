package br.edu.cruzeirodosul.pit.movie;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.cruzeirodosul.pit.exception.ObjectNotFoundException;
import br.edu.cruzeirodosul.pit.movie.dto.CreateMovieDTO;
import br.edu.cruzeirodosul.pit.movie.dto.UpdateMovieDTO;

@Service
public class MovieService {
    
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(String movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));
    }

    public Movie createMovie(CreateMovieDTO movieDTO) {

        Movie movie = new Movie(null, movieDTO.getName(), movieDTO.getGenre(), movieDTO.getSynopsis(), movieDTO.getDirector(), movieDTO.getCountry(), movieDTO.getCasting(), movieDTO.getRating());

        movieRepository.save(movie);

        return movie;
    }

    public void updateMovie(UpdateMovieDTO movieDTO, String movieId) {

        Movie movie = findMovieById(movieId);

        if (movieDTO.getName() != null) {
            movie.setName(movieDTO.getName());
        }

        if (movieDTO.getGenre() != null) {
            movie.setGenre(movieDTO.getGenre());
        }

        if (movieDTO.getSynopsis() != null) {
            movie.setSynopsis(movieDTO.getSynopsis());
        }

        if (movieDTO.getDirector() != null) {
            movie.setDirector(movieDTO.getDirector());
        }

        if (movieDTO.getCountry() != null) {
            movie.setCountry(movieDTO.getCountry());
        }

        if (movieDTO.getCasting()!= null) {
            movie.setCasting(movieDTO.getCasting());
        }

        if (movieDTO.getRating()!= null) {
            movie.setRating(movieDTO.getRating());
        }

        movieRepository.save(movie);
    }

    public void deleteMovie(String movieId) {
        findMovieById(movieId);
        movieRepository.deleteById(movieId);
    }
}
