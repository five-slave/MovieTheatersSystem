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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieTest {

    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private MockService mockService;

    private List<Movie> movieList = new ArrayList<Movie>();
    MockService mockServiceObject = new MockService(new MovieRepository());

    @Before //테스트 하기전에 한번 실행하는거 setup같은느낌
    public void setUp() {


        //5개의 영화 객체를 만듭니다.
        Movie avengers = new Movie("어벤져스", new Movie.ShowTime(0, 2), 10000, 10);
        Movie aladdin = new Movie("알라딘", new Movie.ShowTime(13, 15), 12000, 10);
        Movie lionKing = new Movie("라이언킹", new Movie.ShowTime(15, 17), 9000, 10);
        Movie parasite = new Movie("기생충", new Movie.ShowTime(17, 19), 10000, 10);
        Movie johnWick = new Movie("존 윅", new Movie.ShowTime(20, 22), 9000, 10);

        mockServiceObject.addMovie(avengers);
        mockServiceObject.addMovie(aladdin);
        mockServiceObject.addMovie(lionKing);
        mockServiceObject.addMovie(parasite);
        mockServiceObject.addMovie(johnWick);


    }

    //어벤져스가 영화 리스트에 있는지 확인 후
    // removeMovie를 하였을때 어벤져스가 영화리스트에 없는 지 확인하는 테스트
    @Test
    public void RemoveMovieTest() {
        assertThat(mockServiceObject.findByName("어벤져스"), is(avengers));
        mockServiceObject.removeMovie(mockServiceObject.findByName("어벤져스"));
        assertNull(mockServiceObject.findByName("어벤져스"), is(avengers));
    }

    //거스름돈이 제대로 반환되는지 여부를 확인하는 테스트
    @Test
    public void returnChangeMoneyTest() {
        assertThat(mockServiceObject.returnChangeMoney(avengers, 110000), is(1000));
    }

    //예약시간이 지났음을 알려주는 테스트
    // 0시에 영화가 시작하기 때문에 1시 이후에 test를 진행하면 RuntimeException 발생
    @Test(expected = RuntimeException.class)
    public void ReservationTimeIsPassedTest() {
        assertThat(mockServiceObject.reserveMovie(avengers, 1));
    }

    @Test(expected = RuntimeException.class)
    public void ReservationTimeIsPassedTest() {
        assertThat(mockServiceObject.reserveMovie(avengers, 1));
    }


}
