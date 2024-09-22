package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {
public static Stream<Arguments> dataForYandexMarket () {
    List<String> values = new ArrayList<>();
    values.add("Apple");
//    values.add("ASUS");
//    values.add("BQ");
return Stream.of(
        Arguments.of("Электроника", "Смартфоны", values)
);
}
}
