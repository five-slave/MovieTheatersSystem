package service;

import domain.Movie;
import repository.MockRepository;

import java.util.ArrayList;
import java.util.List;

public class MockService {
    private final MockRepository mockRepository;
    public MockService(MockRepository mockRepository)
    {
        this.mockRepository = mockRepository;
    }
    //******이름을 이용해서 영화를 찾는다********
    public Movie findByName(String searchName)
    {
        Movie movie = mockRepository.findByName(searchName);
        return movie;
    }
    //******사용자가 원하는 시간과 한 시간 내에 있는 영화들을 리스트 안에 담아서 출력
    public List<Movie> findByTime(int movieTime)
    {
        List<Movie> movies = new ArrayList<Movie>();
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
    //******내가 원하는 가격과 영화 가격의 차가 1000원 이하인 영화들을 찾아서 반환해준다****
    public List<Movie> findByPrice (int price)
    {
        List<Movie> movies = new ArrayList<Movie>();
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
    //현재 상영하는 영화를 찾는 함수
    public List<Movie> findAllMovie()
    {
        return mockRepository.findAll();
    }







}
