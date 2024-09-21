package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;

public class YandexBasePage extends BasePage {
    /**
     * xpath кнопки Каталог
     */
   private By catalogue = By.xpath("//div[@data-zone-name='catalog']/button");
    /**
     * xpath разделов
     */
    private By categoryXPath = By.xpath("//li[@data-zone-name='category-link']/a");
    /**
     * xpath категорий
     */
    private By itemsXPath = By.xpath("//ul[@data-autotest-id='subItems']//a");

    private By showMoreButton =  By.xpath("//ul[@data-autotest-id='subItems']//span[@role='button']");

    @Step("Переходим в каталог")
    public YandexBasePage goToCatalogue() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
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
        SelenideElement categories = $$(itemsXPath).findBy(Condition.text(item));
        if(!(categories.exists())) {
           ElementsCollection showMoreButtons  = $$(showMoreButton);
           for (SelenideElement el : showMoreButtons ) {
               el.click();
               if (categories.exists()) {
                   categories.click();
                   break;
               }
           }
            if (!categories.exists()) {
                throw new NoSuchElementException("Категория '" + item + "' не найдена.");
            }
        } else {
            categories.click();
        }
        return new YandexAfterSearchPage();
    }
}

