package service;

import domain.Movie;
import repository.MockRepository;
import repository.MovieRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    //******이름을 이용해서 영화를 찾는다********
    public Movie findByName(String searchName)
    {
        Movie movie = movieRepository.findByName(searchName);
        return movie;
    }

    //******사용자가 원하는 시간과 한 시간 내에 있는 영화들을 리스트 안에 담아서 출력
    public List<Movie> findByTime(int movieTime)
    {
        List<Movie> movies = movieRepository.findByTime(movieTime);
        return movies;
    }

    //******내가 원하는 가격과 영화 가격의 차가 1000원 이하인 영화들을 찾아서 반환해준다****
    public List<Movie> findByPrice (int price)
    {

        List<Movie> movies = movieRepository.findByPrice(price);
        return movies;
    }

    //현재 상영하는 영화를 찾는 함수
    public List<Movie> findAllMovie()
    {
        return movieRepository.findAll();
    }


    public int returnChangeMoney(Movie movie,int receivedMoney){

        int changeMoney = receivedMoney - movie.getPrice();

        if(changeMoney<0){
            logErrorMessage("Not enough money");
            return -1;
        }

        return changeMoney;

    }

    public void addMovie(Movie movie){

        movieRepository.addMovie(movie);
    }


    public void removeMovie(Movie movie){

        movieRepository.removeMovie(movie);
    }

    public int totalPriceOfMovie(Movie movie,int number)
    {
        return movieRepository.totalPriceOfMovie(movie,number);
    }

    public void reserveMovie(Movie movie,int numOfReserveSeat){

        Movie targetMovie = movieRepository.findByName(movie.getName());
        int remainSeat = targetMovie.getSeat()-numOfReserveSeat;

        if(!isVaildMovieReservationTime(movie)){
            logErrorMessage("The reservation time is passed");
            return ;
        }

        if(remainSeat<0){
            logErrorMessage("No Seat remained");
            return ;
        }


        movieRepository.updateSeatOfMovie(targetMovie,remainSeat);

    }

    public boolean isVaildMovieReservationTime(Movie movie){

        SimpleDateFormat formatHour = new SimpleDateFormat ( "HH");

        int currentHour = Integer.parseInt(formatHour.format (System.currentTimeMillis()));

        if(currentHour > movie.getShowtime().getStartTime()){
            return false;
        }

        return true;

    }

    public void logErrorMessage(String msg){
        throw new RuntimeException(msg);
    }


}
