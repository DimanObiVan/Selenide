package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static helpers.Properties.testsProperties;
import static java.lang.Thread.sleep;

public class Scroller {
    public static void scrollWithJS() throws InterruptedException {
        WebDriver chromedriver = getWebDriver();
        sleep(testsProperties.sleepTime());
        JavascriptExecutor js = (JavascriptExecutor) chromedriver;
        WebElement element = chromedriver.findElement(By.xpath("//*[text()='Статистика']"));
        while (true) {try {
            boolean a = (boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                    element
            );
            // Проверяем, видим ли элемент
            if (a) {
                break;
            }

            // Скроллим страницу вниз
            js.executeScript("window.scrollBy(0, 1000);");

            // Ждем некоторое время, чтобы контент успел подгрузиться
            Thread.sleep(testsProperties.sleepTime());

            // Проверяем снова
            if (a) {
                break;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        }
    }
    public static void scrollWithJSq() throws InterruptedException {
        sleep(testsProperties.sleepTime());
       SelenideElement element = $(By.xpath("//*[text()='Статистика']"));
        while (true) {try {
            boolean a = (Selenide.executeJavaScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                    element
            ));
            // Проверяем, видим ли элемент
            if (a) {
                break;
            }

            // Скроллим страницу вниз
            Selenide.executeJavaScript("window.scrollBy(0, 1000);");

            // Ждем некоторое время, чтобы контент успел подгрузиться
            Thread.sleep(testsProperties.sleepTime());

            // Проверяем снова
            if (a) {
                break;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        }
    }
}
