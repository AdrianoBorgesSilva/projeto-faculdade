package br.edu.cruzeirodosul.pit.movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movie")
public class Movie implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private String name;
    private Set<Genre> genre = new HashSet<>();
    private String synopsis;
    private String director;
    private String country;
    private List<String> casting = new ArrayList<>();
    private Double rating;

    public Movie() {
        
    }

    public Movie(String id, String name, Set<Genre> genre, String synopsis, String director, String country, List<String> casting, double rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.synopsis = synopsis;
        this.director = director;
        this.country = country;
        this.casting = casting;
        this.rating = rating;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }  
}
