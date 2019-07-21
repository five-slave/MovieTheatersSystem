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
import service.MovieService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

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
}
