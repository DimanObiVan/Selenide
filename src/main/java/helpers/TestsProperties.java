package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/tests.properties"
})
public interface TestsProperties extends Config {
    @Config.Key("yandexUrl")
    String yandexUrl();

    @Config.Key("sleepTime")
    int sleepTime();
}
