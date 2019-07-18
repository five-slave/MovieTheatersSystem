package repository;

import domain.Movie;

import java.util.List;

public interface MockRepository {

    Movie findByName(String name);

    List<Movie> findByTime(int movieTime);

    List<Movie> findByPrice(int price);

    List<Movie> findAll();

    Movie updateSeatOfMovie(Movie movie, int seat);

    void addMovie(Movie movie);

    void removeMovie(Movie movie);

}
