package br.edu.cruzeirodosul.pit.movie.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import br.edu.cruzeirodosul.pit.movie.Genre;

public class UpdateMovieDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private Set<Genre> genre;
    private String synopsis;
    private String director;
    private String country;
    private List<String> casting;
    private Double rating;

    public UpdateMovieDTO() {

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCasting() {
        return casting;
    }

    public void setCasting(List<String> casting) {
        this.casting = casting;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
