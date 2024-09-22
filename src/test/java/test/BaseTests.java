package test;

import allure.selenide.CustomAllureSelenide;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.open;
import static helpers.Properties.testsProperties;

public class BaseTests {
    @BeforeAll
    public static void beforeAll(){
        Configuration.timeout = 10000;
        SelenideLogger.addListener("AllureSelenide",new CustomAllureSelenide().screenshots(true).savePageSource(true));

    }

//    @AfterAll
//    public static void tearDown(){
//        Selenide.closeWebDriver();
//
//    }
}
