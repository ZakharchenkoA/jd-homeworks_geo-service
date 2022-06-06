import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoServiceImplTest {

    static Location mskLenina = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
    static Location nyAvenue = new Location("New York", Country.USA, "10th Avenue", 32);
    static Location mskNull = new Location("Moscow", Country.RUSSIA, null, 0);
    static Location nyNull = new Location("New York", Country.USA, null, 0);

    @ParameterizedTest
    @MethodSource("source")
    void byIpTest(String arg, Location expected) {
        Location[] locationsArr = {mskLenina, mskNull, nyAvenue, nyNull};
        GeoServiceImpl geoTest = new GeoServiceImpl();

        Location location = geoTest.byIp(arg);
        for (Location value : locationsArr) {
            if (location.equals(value)) {
                location = value;
                assertEquals(expected, location);
            }
        }
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.MOSCOW_IP, mskLenina),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, nyAvenue),
                Arguments.of("172.", mskNull),
                Arguments.of("96.", nyNull));
    }
}