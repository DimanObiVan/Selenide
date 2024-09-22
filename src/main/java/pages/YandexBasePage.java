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
    /**
     * xpath кнопок Показать еще
     */
    private By showMoreButton =  By.xpath("//ul[@data-autotest-id='subItems']//span[@role='button']");
    /**
     * Переход в каталог с увеличением окна браузера до максимального размера.
     *
     * @return текущая страница YandexBasePage
     */
    @Step("Переходим в каталог")
    public YandexBasePage goToCatalogue() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
        $(catalogue).click();
        return this;
    }
    /**
     * Наведение курсора на указанный раздел.
     *
     * @param category Название раздела, на который нужно навести курсор.
     * @return текущая страница YandexBasePage
     */
    @Step("Навести курсор на раздел {category}")
    public YandexBasePage hoverOver(String category) {
        $$(categoryXPath)
                .findBy(Condition
                        .text(category))
                .hover();
        return this;
    }
    /**
     * Переход в указанный раздел после нажатия кнопки "Показать еще" при необходимости.
     * Если категория не найдена после нажатий, бросает исключение {@link NoSuchElementException}.
     *
     * @param item Название элемента раздела, в который необходимо перейти.
     * @return страница YandexAfterSearchPage после перехода
     * @throws NoSuchElementException если категория не найдена
     */
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

