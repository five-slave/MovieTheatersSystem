package repository;

import domain.Movie;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MovieRepository implements MockRepository {

    private List<Movie> movies = new ArrayList<Movie>();

    @Override
    public Movie findByName(String name) {

        for(Movie m : movies){
            if(m.getName().equals(name))
                return m;
        }

        return null;
    }

    @Override
    public List<Movie> findByTime(int movieTime) {

        List<Movie> suitableMovie = new ArrayList<Movie>();
        for(Movie m : movies)
        {
            if(m.getShowtime().getStartTime() - movieTime <= 1)
            {
                suitableMovie.add(m);
            }
        }
        return suitableMovie;
    }

    @Override
    public List<Movie> findByPrice(int price) {

        List<Movie> priceMovies = new ArrayList<Movie>();

        for(Movie m : movies)
        {
            if(Math.abs(m.getPrice() - price) <= 1000)
            {
                priceMovies.add(m);
            }
        }
        return priceMovies;
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }

    @Override
    public Movie updateSeatOfMovie(Movie movie, int seat) {

        Movie updatedMovie = findByName(movie.getName());

        updatedMovie.setSeat(seat);

        return updatedMovie;
    }

    @Override
    public void addMovie(Movie movie) {
        if(isValidMovie(movie))
            movies.add(movie);

    }

    @Override
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public boolean isValidMovie(Movie movie){

        for(Movie m : movies){
            if(isDuplicateTwoMovieTime(m,movie)){
                logErrorMessage("Duplicate Movie");
                return false;
            }
        }

        return true;
    }

    public boolean isDuplicateTwoMovieTime(Movie movie1, Movie movie2){
        Movie lateMovie,earlyMovie;

        if(movie1.getShowtime().getStartTime()<movie2.getShowtime().getStartTime()){
            lateMovie = movie2;
            earlyMovie = movie1;
        }else if(movie1.getShowtime().getStartTime()>movie2.getShowtime().getStartTime()){
            lateMovie = movie1;
            earlyMovie = movie2;
        }else
            return true;

        if(lateMovie.getShowtime().getStartTime()<earlyMovie.getShowtime().getEndTime())
            return true;

        return false;

    }

    public void logErrorMessage(String msg){
        throw new RuntimeException(msg);
    }
}
