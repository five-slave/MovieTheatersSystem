import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.MockRepository;
import service.MockService;

@RunWith(MockitoJUnitRunner.class)
public class MovieTest {

    @Mock
    private MockRepository mockRepository;

    @InjectMocks
    private MockService mockService;


}