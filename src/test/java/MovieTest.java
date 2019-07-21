import domain.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;
import repository.MovieRepository;
import service.MockService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieTest {


    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MockService mockService;

    private List<Movie> movieList = new ArrayList<Movie>();
    //MockService mockServiceObject = new MockService(new MockRepository());


    //@Before //테스트 하기전에 한번 실행하는거 setup같은느낌
    //public void setUp() {

    //5개의 영화 객체를 만듭니다.
    Movie avengers = new Movie("어벤져스", new Movie.ShowTime(0, 2), 10000, 10);
    Movie aladdin = new Movie("알라딘", new Movie.ShowTime(13, 15), 12000, 10);
    Movie lionKing = new Movie("라이언킹", new Movie.ShowTime(15, 17), 9000, 10);
    Movie parasite = new Movie("기생충", new Movie.ShowTime(17, 19), 10000, 10);
    Movie johnWick = new Movie("존 윅", new Movie.ShowTime(20, 22), 9000, 10);






        /*mockServiceObject.addMovie(aladdin);
        mockServiceObject.addMovie(lionKing);
        mockServiceObject.addMovie(parasite);
        mockServiceObject.addMovie(johnWick);*/


    //findByname test
    @Test
    public void findByNameTest() {
        when(mockService.findByName("어벤져스")).thenReturn(new Movie("어벤져스", new Movie.ShowTime(0, 2), 10000, 10));
        String movieName = mockService.findByName("어벤져스").getName();
        assertThat(movieName, is("어벤져스"));
    }

    //시간검색 테스트
    @Test
    public void 원하는시간과_1시간차이나는_영화를_1번_검색했는지_findByTimeTest() {
        mockService.addMovie(avengers);
        mockService.addMovie(aladdin);
        mockService.addMovie(lionKing);
        mockService.addMovie(parasite);
        mockService.addMovie(johnWick);
        List<Movie> suitablemovies = mockService.findByTime(15);
        verify(movieRepository, times(1)).findByTime(anyInt());
        // assertThat(suitablemovies.size(),is(1));

    }

    // removeMovie를 하였을때 어벤져스가 영화리스트에 없는 지 확인하는 테스트
    @Test //일반 테스트로 하는경우
    public void RemoveMovieTest() {
        mockService.addMovie(avengers);
        mockService.addMovie(aladdin);
        mockService.removeMovie(avengers);
        assertNull(mockService.findByName("어벤져스"));

    }

    //거스름돈이 제대로 반환되는지 여부를 확인하는 테스트
    @Test
    public void returnChangeMoneyTest() {
        assertThat(mockService.returnChangeMoney(avengers, 110000), is(1000));
    }

    //예약시간이 지났음을 알려주는 테스트
    // 0시에 영화가 시작하기 때문에 1시 이후에 test를 진행하면 RuntimeException 발생
    @Test(expected = RuntimeException.class)
    public void ReservationTimeIsPassedTest() {
        assertTrue(mockService.reserveMovie(avengers, 1));
    }

    //라이언킹을 3명이 예매하려고 할 때, 그 가격이 맞는지 확인하는 테스트
    @Test
    public void priceOf_LionKing_three_People()
    {
        when(mockService.priceOfMovie(lionKing,3)).thenReturn(27000);
        int price = mockService.priceOfMovie(lionKing,3);
        assertThat(price,is(27000));

    }
}

