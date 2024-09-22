package test;

import com.codeborne.selenide.*;
import helpers.Scroller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.YandexBasePage;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.Properties.testsProperties;
import static helpers.Wait.waitForStaleness;

public class YandexTest extends BaseTests {
    @ParameterizedTest
    @MethodSource("helpers.DataProvider#dataForYandexMarket")
    public void test1(String category, String item, List<String> values) throws InterruptedException {
        open(testsProperties.yandexUrl(), YandexBasePage.class)
                .goToCatalogue()
                .hoverOver(category)
                .goToItem(item)
                .verifyCategory(item)
                .setManufacturers(values)
                .assertElementsMatchFilter(values);
    }
    }

