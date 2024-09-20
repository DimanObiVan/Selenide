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
    public void test(String category, String item, List<String> values) throws InterruptedException {
        open("https://market.yandex.ru/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        $(By.xpath("//div[@data-zone-name='catalog']/button")).click();
        $$(By.xpath("//li[@data-zone-name='category-link']/a"))
                .findBy(Condition
                        .text(category))
                .hover();
        $$(By.xpath("//ul[@data-autotest-id='subItems']//a"))
                .findBy(Condition
                        .text(item)).click();
        $(By.xpath("//h1[@data-auto='title']"))
                .shouldHave(Condition
                        .text(item));
        for (String value : values) {
            //Ждем появление кнопки Показать еще и нажимаем
            $(By.xpath("//div[contains(@data-zone-data,'Производитель')]//button")).click();
            //Ждем появление поля для ввода и вводим название
            $(By.xpath("//div[contains(@data-zone-data,'Производитель')]//input")).sendKeys(value);
            //Ждем появление фильтра по названию и отмечаем чекбокс
            $$(By.xpath("//div[contains(@data-zone-data,'Производитель')]//*[@role='checkbox']"))
                    .findBy(Condition.text(value))
                    .click();
            ;
            //ждем, когда прогрузится список
            waitForStaleness($(By.xpath("//div[@data-apiary-widget-name='@marketfront/SerpEntity'][2]")));
        }
        sleep(5000);
        Scroller.scrollWithJSq();
        sleep(5000);

       ElementsCollection elements =  $$(By.xpath("//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']"));
        List<String> nonMatchingElements = elements.stream()
                .map(SelenideElement::getText)
                .filter(text ->values.stream().noneMatch(text::contains))
                        .collect(Collectors.toList());
        Assertions.assertTrue(nonMatchingElements.isEmpty(),
                "Не все элементы соответствуют фильтру! Несоответствующие элементы: " + nonMatchingElements);

    }

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

