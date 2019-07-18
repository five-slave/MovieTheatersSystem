package repository;

import domain.Movie;

import java.util.List;

public interface MockRepository {

    Movie findByName(String name);

    List<Movie> findByTime(int time);

    List<Movie> findByPrice(int price);

    List<Movie> findAll();

}
