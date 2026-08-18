package com.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base Test class providing WebDriver setup, browser lifecycle management, and shared configuration
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://the-internet.herokuapp.com/login";

    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver automatically using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        // Optional headless mode support (e.g. mvn test -Dheadless=true or in CI/CD)
        String headless = System.getProperty("headless", "false");
        if (Boolean.parseBoolean(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    /**
     * Helper to navigate directly to the application login page
     */
    public void navigateToLoginPage() {
        driver.get(BASE_URL);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
