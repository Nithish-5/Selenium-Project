package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.SecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Suite verifying Login Form scenarios on the-internet.herokuapp.com/login
 * 
 * Implements:
 * - Test Case 1: Valid Login
 * - Test Case 2: Invalid Login
 * - Test Case 3: Empty Fields Login
 */
public class LoginTest extends BaseTest {

    /**
     * Test Case 1: Valid Login
     * Steps:
     * 1. Navigate to the login page.
     * 2. Enter a valid username ("tomsmith") and password ("SuperSecretPassword!").
     * 3. Click the Login button.
     * 4. Assert that the user is successfully logged in and redirected to the "Secure Area" page.
     * 5. Assert that the success banner and secure area heading are displayed.
     */
    @Test(priority = 1, description = "Test Case 1: Verify successful login with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        // Perform login with valid credentials
        loginPage.login("tomsmith", "SuperSecretPassword!");

        // Validate redirection to Secure Area page
        SecureAreaPage secureAreaPage = new SecureAreaPage(driver);
        Assert.assertTrue(secureAreaPage.isAt(), 
                "User should be redirected to the Secure Area page (/secure). Current URL: " + driver.getCurrentUrl());

        // Validate Secure Area page header
        String headingText = secureAreaPage.getHeadingText();
        Assert.assertTrue(headingText.contains("Secure Area"), 
                "Page heading should contain 'Secure Area'. Actual: " + headingText);

        // Validate success flash message
        String flashMessage = secureAreaPage.getFlashMessageText();
        Assert.assertTrue(flashMessage.contains("You logged into a secure area!"), 
                "Flash banner should confirm successful login. Actual: " + flashMessage);

        // Validate logout button is available
        Assert.assertTrue(secureAreaPage.isLogoutButtonDisplayed(), 
                "Logout button should be visible on the Secure Area page.");
    }

    /**
     * Test Case 2: Invalid Login
     * Steps:
     * 1. Navigate to the login page.
     * 2. Enter an invalid username ("wrongusername") and password ("wrongpassword").
     * 3. Click the Login button.
     * 4. Assert that an error message ("Your username is invalid!") is displayed.
     * 5. Assert that the user remains on the login page.
     */
    @Test(priority = 2, description = "Test Case 2: Verify error message with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        // Perform login with invalid credentials
        loginPage.login("wrongusername", "wrongpassword");

        // Validate that flash message is displayed and indicates error
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(), 
                "An error message banner should be displayed.");

        String flashText = loginPage.getFlashMessageText();
        Assert.assertTrue(flashText.contains("Your username is invalid!"), 
                "Error message should contain 'Your username is invalid!'. Actual: " + flashText);

        // Validate user remains on login page
        Assert.assertTrue(loginPage.isAt(), 
                "User should remain on the login page after a failed login attempt.");
    }

    /**
     * Test Case 3: Empty Fields
     * Steps:
     * 1. Navigate to the login page.
     * 2. Leave both username and password fields empty.
     * 3. Click the Login button.
     * 4. Assert that an error message is displayed.
     * 5. Assert that the user remains on the login page.
     */
    @Test(priority = 3, description = "Test Case 3: Verify error message when username and password fields are empty")
    public void testEmptyFieldsLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        // Submit form with empty credentials
        loginPage.login("", "");

        // Validate that error banner is displayed
        Assert.assertTrue(loginPage.isFlashMessageDisplayed(), 
                "An error message banner should be displayed when submitting empty credentials.");

        String flashText = loginPage.getFlashMessageText();
        // The herokuapp login form displays "Your username is invalid!" on empty field submission
        Assert.assertTrue(flashText.contains("Your username is invalid!") || flashText.contains("required") || loginPage.isErrorFlashDisplayed(), 
                "Error message banner should indicate invalid / required fields. Actual: " + flashText);

        // Validate user remains on login page
        Assert.assertTrue(loginPage.isAt(), 
                "User should remain on the login page after empty field submission.");
    }
}
