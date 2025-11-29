package tests;

import base.BaseTest;
import core.LoggerManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login functionality tests
 * Core tests for login feature
 */
@Epic("Mobile App Testing")
@Feature("Login Feature")
public class LoginTest extends BaseTest {

    @Test(description = "Verify Login page is displayed after app launch")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login Page Display")
    public void testLoginPageDisplayed() {
        captureStep("LoginPage_Initial");

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be displayed");
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
        Assert.assertTrue(loginPage.isSubmitButtonDisplayed(), "Submit button should be displayed");

        LoggerManager.success("Login page elements verified");
    }

    @Test(description = "Verify successful login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login")
    public void testLoginWithValidCredentials() {
        loginPage.enterUsername("admin");
        captureStep("Username_Entered");

        loginPage.enterPassword("password");
        captureStep("Password_Entered");

        listPage = loginPage.clickSubmit();
        core.WaitHelper.sleep(10000); // Wait for login animation

        captureStep("After_Login");

        Assert.assertTrue(listPage.isPageLoaded(), "Should navigate to List page after successful login");
        LoggerManager.success("Login successful");
    }

    @Test(description = "Verify login with empty credentials")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login Validation")
    public void testLoginWithEmptyCredentials() {
        loginPage.clearUsername();
        loginPage.clearPassword();

        loginPage.clickSubmit();
        core.WaitHelper.sleep(2000);

        captureStep("Empty_Credentials_Submit");

        Assert.assertTrue(loginPage.isPageLoaded(), "Should stay on login page with empty credentials");
        LoggerManager.success("Empty credentials handled correctly");
    }
}
