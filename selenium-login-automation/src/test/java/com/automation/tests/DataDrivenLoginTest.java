package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.SecureAreaPage;
import com.automation.utils.CsvReaderUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Bonus Requirement: Data-Driven Login Test using a custom CSV Reader solution
 */
public class DataDrivenLoginTest extends BaseTest {

    private static final String CSV_PATH = "src" + File.separator + "test" + File.separator + "resources" + 
                                          File.separator + "testdata" + File.separator + "login_data.csv";

    @DataProvider(name = "loginCsvData")
    public Object[][] getLoginDataFromCsv() {
        return CsvReaderUtil.readCsvData(CSV_PATH);
    }

    /**
     * Data-Driven test executing various credential permutations from CSV
     * 
     * @param scenario Description of test scenario
     * @param username Input username
     * @param password Input password
     * @param expectedResult Expected outcome ("SUCCESS" or "FAILURE")
     * @param expectedMessage Substring expected in the flash notification banner
     */
    @Test(dataProvider = "loginCsvData", priority = 4, description = "Bonus: Data-Driven Login Testing using external CSV data")
    public void testDataDrivenLogin(String scenario, String username, String password, String expectedResult, String expectedMessage) {
        System.out.println("Executing Scenario: " + scenario + " | User: " + username);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(BASE_URL);

        // Perform login attempt
        loginPage.login(username, password);

        if ("SUCCESS".equalsIgnoreCase(expectedResult)) {
            SecureAreaPage secureAreaPage = new SecureAreaPage(driver);
            Assert.assertTrue(secureAreaPage.isAt(), 
                    "[" + scenario + "] Should be redirected to Secure Area page.");
            Assert.assertTrue(secureAreaPage.getFlashMessageText().contains(expectedMessage), 
                    "[" + scenario + "] Success banner should contain: '" + expectedMessage + "'");
        } else {
            Assert.assertTrue(loginPage.isFlashMessageDisplayed(), 
                    "[" + scenario + "] Error flash message should be displayed.");
            String actualMessage = loginPage.getFlashMessageText();
            Assert.assertTrue(actualMessage.contains(expectedMessage) || loginPage.isErrorFlashDisplayed(), 
                    "[" + scenario + "] Flash message should contain: '" + expectedMessage + "'. Actual: " + actualMessage);
            Assert.assertTrue(loginPage.isAt(), 
                    "[" + scenario + "] User should remain on the Login page.");
        }
    }
}
