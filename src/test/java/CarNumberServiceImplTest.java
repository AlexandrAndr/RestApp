import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.meschanov.rest.repository.CarNumberRepository;
import ru.meschanov.rest.service.impl.CarNumberServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CarNumberServiceImplTest {

    @Mock
    private CarNumberRepository carNumberRepository;

    @InjectMocks
    private CarNumberServiceImpl service;

    @Test
    public void shouldGetRandomCarNumberTest(){

        String carNumber = service.getRandomCarNumber();
        System.out.println(carNumber);

        String expected = carNumber;
        String actual = carNumber;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerationNumbIntoStringTest() {

        int someNumb = 546;

        String numbIntoString = service.generationNumbIntoString(someNumb);

        String expected = "546";

        String actual = numbIntoString;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetNextNumberTest() {

        String number = "М999АА 116 RUS";

        String nextNumber = service.getNextNumber(number);

        String expected = "М001АВ 116 RUS";

        String actual = nextNumber;

        Assert.assertEquals(expected, actual);

    }
}
