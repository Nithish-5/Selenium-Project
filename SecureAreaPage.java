package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model representing the Secure Area Page after successful login
 */
public class SecureAreaPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By heading = By.tagName("h2");
    private final By subHeading = By.className("subheader");
    private final By flashMessage = By.id("flash");
    private final By logoutButton = By.cssSelector("a.button.secondary.radius, a[href='/logout']");

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Check if currently located on the Secure Area Page
     * @return true if URL contains /secure and heading displays 'Secure Area'
     */
    public boolean isAt() {
        try {
            wait.until(ExpectedConditions.urlContains("/secure"));
            WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
            return headerElement.isDisplayed() && headerElement.getText().contains("Secure Area");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve the heading text
     * @return heading text
     */
    public String getHeadingText() {
        WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return headerElement.getText().trim();
    }

    /**
     * Retrieve the sub-heading description text
     * @return subheader text
     */
    public String getSubHeadingText() {
        WebElement subHeaderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subHeading));
        return subHeaderElement.getText().trim();
    }

    /**
     * Retrieve the text content of the success flash banner
     * @return flash message text
     */
    public String getFlashMessageText() {
        WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return flash.getText().trim();
    }

    /**
     * Click the Logout button to return to the login screen
     */
    public void clickLogout() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        button.click();
    }

    /**
     * Check if the logout button is visible
     * @return true if logout button is displayed
     */
    public boolean isLogoutButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
