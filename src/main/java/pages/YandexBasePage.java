package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class YandexBasePage extends BasePage {
    /**
     * xpath кнопки Каталог
     */
    By catalogue = By.xpath("//div[@data-zone-name='catalog']/button");
    /**
     * xpath разделов
     */
    By categoryXPath = By.xpath("//li[@data-zone-name='category-link']/a");
    /**
     * xpath категорий
     */
    By itemsXPath = By.xpath("//ul[@data-autotest-id='subItems']//a");

    @Step("Переходим в каталог")
    public YandexBasePage goToCatalogue() {
        $(catalogue).click();
        return this;
    }

    @Step("Навести курсор на раздел {category}")
    public YandexBasePage hoverOver(String category) {
        $$(categoryXPath)
                .findBy(Condition
                        .text(category))
                .hover();
        return this;
    }
    @Step("Перейти в раздел {item}")
    public YandexAfterSearchPage goToItem(String item) {
        $$(itemsXPath)
                .findBy(Condition
                        .text(item)).click();
        return new YandexAfterSearchPage();
    }
}

