package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model representing the Login Page on the-internet.herokuapp.com
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");
    private final By pageHeader = By.tagName("h2");
    private final By subHeader = By.tagName("h4");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigate directly to the login page URL
     * @param url the full URL to the login page
     */
    public void navigateTo(String url) {
        driver.get(url);
    }

    /**
     * Enter text into the username input field
     * @param username the username string
     */
    public void enterUsername(String username) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userField.clear();
        if (username != null && !username.isEmpty()) {
            userField.sendKeys(username);
        }
    }

    /**
     * Enter text into the password input field
     * @param password the password string
     */
    public void enterPassword(String password) {
        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passField.clear();
        if (password != null && !password.isEmpty()) {
            passField.sendKeys(password);
        }
    }

    /**
     * Click the login submit button
     */
    public void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    /**
     * Helper method to perform full login action
     * @param username the username to input
     * @param password the password to input
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Retrieve the text content of the flash notification banner
     * @return the text of the flash message
     */
    public String getFlashMessageText() {
        WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return flash.getText().trim();
    }

    /**
     * Check whether the flash alert message is displayed
     * @return true if visible, false otherwise
     */
    public boolean isFlashMessageDisplayed() {
        try {
            WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
            return flash.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the flash alert indicates an error (has class 'error')
     * @return true if error class is present
     */
    public boolean isErrorFlashDisplayed() {
        try {
            WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
            String classAttribute = flash.getAttribute("class");
            return flash.isDisplayed() && classAttribute != null && classAttribute.contains("error");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve the main page header text
     * @return header text
     */
    public String getPageHeaderText() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
        return header.getText().trim();
    }

    /**
     * Check if on Login Page by checking header and input fields
     * @return true if on login page
     */
    public boolean isAt() {
        return driver.getCurrentUrl().contains("/login") && 
               driver.findElement(pageHeader).isDisplayed() &&
               driver.findElement(usernameInput).isDisplayed();
    }
}
