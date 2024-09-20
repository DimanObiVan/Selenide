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

public class BaseTests {
    @BeforeAll
    public static void beforeAll(){
        SelenideLogger.addListener("AllureSelenide",new CustomAllureSelenide().screenshots(true).savePageSource(true));

    }

//    @AfterAll
//    public static void tearDown(){
//        Selenide.closeWebDriver();
//
//    }
}
