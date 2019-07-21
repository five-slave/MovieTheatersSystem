import domain.Movie;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;
import repository.MovieRepository;
import service.MockService;
import service.MovieService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieTest {

    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private MockService mockService;

    private MovieService movieService;

    @Before
    public void setUp() {
        movieService = new MovieService( new MovieRepository());
        //5개의 영화 객체를 만듭니다.
        Movie avengers = new Movie("어벤져스", new Movie.ShowTime(0, 2), 10000, 10);
        Movie aladdin = new Movie("알라딘", new Movie.ShowTime(13, 15), 12000, 10);
        Movie lionKing = new Movie("라이언킹", new Movie.ShowTime(15, 17), 9000, 10);
        Movie parasite = new Movie("기생충", new Movie.ShowTime(17, 19), 10000, 10);
        Movie johnWick = new Movie("존 윅", new Movie.ShowTime(20, 22), 9000, 10);

        movieService.addMovie(avengers);
        movieService.addMovie(aladdin);
        movieService.addMovie(lionKing);
        movieService.addMovie(parasite);
        movieService.addMovie(johnWick);

    }

    @Test
    public void findByNameTest(){
        Movie movie = movieService.findByName("어벤져스");
        assertThat(movie.getName(),is("어벤져스"));
    }

    @Test
    public void shouldMovieSizeIsFive(){
        List<Movie> movies = movieService.findAllMovie();
        assertThat(movies.size(),is(5));
    }

    @Test
    public void findByPriceTest(){

        List<String> movieNames1 = movieService.findByPrice(10000).stream()
                                    .map(m->m.getName())
                                    .collect(Collectors.toList());

        List<String> movieNames2 =  new ArrayList<>(Arrays.asList("어벤져스", "라이언킹", "기생충", "존 윅"));

        assertThat(movieNames1,samePropertyValuesAs(movieNames2));
    }

    @Test
    public void findByTimeTest(){
        List<String> movieNames1 = movieService.findByTime(0).stream()
                                    .map(m->m.getName())
                                    .collect(Collectors.toList());
        List<String> movieNames2 = new ArrayList<>(Arrays.asList("어벤져스"));

        assertThat(movieNames1,samePropertyValuesAs(movieNames2));
    }

    @Test
    public void reserveMovieTest(){
        Movie reserveMovie =movieService.findByName("존 윅");
        movieService.reserveMovie(reserveMovie,1);

        assertThat(reserveMovie,is(9));

    }

    @Test(expected = RuntimeException.class)
    public void shouldOverReservationIsExpectedToRunTimeExceptionTest(){
        Movie reserveMovie =movieService.findByName("존 윅");
        movieService.reserveMovie(reserveMovie,11);

    }

    @Test
    public void checkNumOfMovieSeat(){
        when(mockService.findByName("어벤져스"))
                .thenReturn(new Movie("어벤져스",new Movie.ShowTime(0,2),10000,10));

        Movie movie = mockService.findByName("어벤져스");

        verify(mockRepository,times(1)).findByName(anyString());
        assertThat(movie.getSeat(),is(10));

    }

    @Test
    public void shouldChangeMoneyIs1000WhenPay10000WonForWatchingMovieWhosePriceIs9000Won(){
        Movie movie = mock(Movie.class);

        when(movie.getPrice()).thenReturn(9000);

        assertThat(mockService.returnChangeMoney(movie,10000),is(1000));
    }

    @Test(expected = RuntimeException.class)
    public void expectRuntimeExceptionWhenReservePassedMovie(){
        SimpleDateFormat formatHour = new SimpleDateFormat ( "HH");

        int currentHour = Integer.parseInt(formatHour.format (System.currentTimeMillis()));

        Movie movie = mock(Movie.class);

        given(mockRepository.findByName(anyString()))
                .willReturn(new Movie(anyString(),new Movie.ShowTime(currentHour-1,currentHour+1),10000,10));

        mockService.reserveMovie(movie,1);

    }

    @Test(expected = RuntimeException.class)
    public void expectRunTimeExceptionWhenPay9000WonForWatchingMovieWhosePriceIs10000Won(){
        Movie movie = mock(Movie.class);

        when(movie.getPrice()).thenReturn(10000);

        mockService.returnChangeMoney(movie,9000);
    }

    @Test
    public void shouldUpdateSeatOfMovieMethodOccurOneTimeWhenReserveMovieBeforeTheMovieStartTime(){

        SimpleDateFormat formatHour = new SimpleDateFormat ( "HH");

        int currentHour = Integer.parseInt(formatHour.format (System.currentTimeMillis()));

        Movie movie = mock(Movie.class);


        given(mockRepository.findByName(anyString()))
                .willReturn(new Movie("어벤져스",new Movie.ShowTime(currentHour+1,currentHour+3),10000,10));

        mockService.reserveMovie(movie,1);

        verify(mockRepository,times(1)).updateSeatOfMovie(any(Movie.class),any(Integer.class));
    }

    @Test
    public void addMovieTest(){
        Movie movie = new Movie("스파이더맨", new Movie.ShowTime(3, 5), 10000, 10);

        movieService.addMovie(movie);

        assertThat(movieService.findByName("스파이더맨"),is(movie));

    }

    @Test
    public void removeMovieTest(){
        Movie movie = new Movie("스파이더맨", new Movie.ShowTime(3, 5), 10000, 10);

        movieService.addMovie(movie);

        assertThat(movieService.findByName("스파이더맨"),is(movie));

        movieService.removeMovie(movie);

        assertNull(movieService.findByName("스파이더맨"));
    }


}
