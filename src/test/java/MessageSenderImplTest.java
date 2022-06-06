import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderImplTest {

    @ParameterizedTest
    @MethodSource("source")
    public void sendTest(String ip, String expected, Country country, Location location) {
        GeoService geoServiceTest = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceTest.byIp(ip))
                .thenReturn(location);

        LocalizationService localServiceTest = Mockito.mock(LocalizationService.class);
        Mockito.when(localServiceTest.locale(country))
                .thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoServiceTest, localServiceTest);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);

        assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("96", "Welcome", Country.USA,
                        new Location("New York", Country.USA, null, 0)),
                Arguments.of("172", "Добро пожаловать", Country.RUSSIA,
                        new Location("Moscow", Country.RUSSIA, null, 0))
        );
    }
}