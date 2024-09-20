package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import helpers.Scroller;
import io.qameta.allure.Step;
import helpers.Assertions;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static helpers.Wait.waitForStaleness;

public class YandexAfterSearchPage extends BasePage{
    /**
     * название страницы
     */
    private By title = By.xpath("//h1[@data-auto='title']");
    /**
     * Кнопка показать еще
     */
    private By openMoreButton = By.xpath("//div[contains(@data-zone-data,'Производитель')]//button");
    /**
     * Поле ввода производителя
     */
    private By inputField = By.xpath("//div[contains(@data-zone-data,'Производитель')]//input");
    /**
     * чекбоксы производителей
     */
    private By checkboxes = By.xpath("//div[contains(@data-zone-data,'Производитель')]//*[@role='checkbox']");

    /**
     * Первый элемент в списке для ожидания прогрузки страницы
     */
    private By forStaleness = By.xpath("//div[@data-apiary-widget-name='@marketfront/SerpEntity']");
    /**
     * Список названий товаров
     */
    private By itemList = By.xpath("//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']");


    /**
     * ПРоверка перехода в нужный раздел
     * @param item - название раздела
     * @return объект YandexAfterSearchPage
     */
    @Step("Убедится что вы перешли в раздел {item}")
    public YandexAfterSearchPage verifyCategory(String item) {
        $(title)
                .shouldHave(Condition
                        .text(item));
        return this;
    }

    /**
     * Метод для работы с фильтром производителей на странице.
     * @param values список значений производителей, которые необходимо выбрать
     *
     * @return объект YandexAfterSearchPage
     */
    @Step("Задать параметр «Производитель» {values}")
    public YandexAfterSearchPage setManufacturers(List<String> values) {
        for (String value : values) {
            //Ждем появление кнопки Показать еще и нажимаем
            $(openMoreButton).click();
            //Ждем появление поля для ввода и вводим название
            $(inputField).sendKeys(value);
            //Ждем появление фильтра по названию и отмечаем чекбокс
            $$(checkboxes)
                    .findBy(Condition.text(value))
                    .click();

            ;
            //ждем, когда прогрузится список
            waitForStaleness($$(forStaleness).get(1));
        }
        return this;
    }

    /**
     * Метод проверки на соответствие фильтру
     * @param values список производителей (Кузнецов)
     * @return  объект YandexAfterSearchPage
     * @throws InterruptedException в случае, если в процессе выполнения возникли проблемы с ожиданием
     */
    @Step("Убедится что в выборку попали только {values}")
    public YandexAfterSearchPage assertElementsMatchFilter(List<String> values) throws InterruptedException {
        Scroller.scrollWithJSq();
        ElementsCollection elements =  $$(itemList);
        List<String> nonMatchingElements = elements.stream()
                .map(SelenideElement::getText)
                .filter(text ->values.stream().noneMatch(text::contains))
                .collect(Collectors.toList());

        Assertions.assertTrue(nonMatchingElements.isEmpty(),
                "Не все элементы соответствуют фильтру! Несоответствующие элементы: " + nonMatchingElements);

        return this;
    }

}
