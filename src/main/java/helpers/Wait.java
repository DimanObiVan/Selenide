package helpers;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Wait {
    /**
     * Ждем когда элемент станет stale
     * @param element - элемент для ожидания (Кузнецов)
     */
    public static void waitForStaleness(SelenideElement element) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(element.getWrappedElement()));
    }
}
