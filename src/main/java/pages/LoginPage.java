package pages;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import base.BasePage;

/**
 * Login Page Object
 * Supports both Android and iOS platforms
 */
public class LoginPage extends BasePage {

    // ==================== Locators ====================
    
    private final By usernameField = By.id("com.example.trusttest:id/editTextUsername");
    private final By passwordField = By.id("com.example.trusttest:id/editTextPassword");
    private final By submitButton = By.id("com.example.trusttest:id/buttonSubmit");
    private final By loginTitle = By.xpath("//android.widget.TextView[@text='Login']");

    
    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(loginTitle, 10);
    }

    public LoginPage enterUsername(String username) {
        LoggerManager.step("Entering username: " + username);
        type(usernameField, username);
        return this;
    }


    public LoginPage enterPassword(String password) {
        LoggerManager.step("Entering password");
        type(passwordField, password);
        return this;
    }

    public ListPage clickSubmit() {
        LoggerManager.step("Clicking Submit button");
        click(submitButton);
        return new ListPage(driver);
    }

    public ListPage login(String username, String password) {
        LoggerManager.info("Performing login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        hideKeyboard();
        
        clickSubmit();
        
        LoggerManager.info("Waiting for login to complete...");
        WaitHelper.sleep(10000);
        
        LoggerManager.success("Login completed");
        return new ListPage(driver);
    }

    public boolean isSubmitButtonDisplayed() {
        return isDisplayed(submitButton);
    }

    public boolean isUsernameFieldDisplayed() {
        return isDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isDisplayed(passwordField);
    }

    public LoginPage clearUsername() {
        clear(usernameField);
        return this;
    }

    public LoginPage clearPassword() {
        clear(passwordField);
        return this;
    }
}
