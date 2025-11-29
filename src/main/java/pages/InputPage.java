package pages;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import base.BasePage;

/**
 * Input Tab Page Object (renamed from CryptoPage)
 * Supports both Android and iOS platforms
 * Design Pattern: Page Object Model
 */
public class InputPage extends BasePage {

    // ==================== Locators ====================
    
    private final By inputValue = By.id("com.example.trusttest:id/inputValue");
    
    // Tab locators for navigation
    private final By listTab = By.xpath("//android.widget.LinearLayout[@content-desc='List']");
    private final By buttonsTab = By.xpath("//android.widget.LinearLayout[@content-desc='Buttons']");
    private final By switchesTab = By.xpath("//android.widget.LinearLayout[@content-desc='Switches']");

    // ==================== Constructor ====================
    
    public InputPage(AppiumDriver driver) {
        super(driver);
    }

    // ==================== Page Verification ====================

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(inputValue, 5);
    }

    // ==================== Input Operations ====================

    public void enterValue(String value) {
        LoggerManager.step("Entering value: " + value);
        clear(inputValue);
        type(inputValue, value);
    }

    public String getValue() {
        return getText(inputValue);
    }

    public void clearValue() {
        LoggerManager.step("Clearing input value");
        clear(inputValue);
    }

    // ==================== Verification Methods ====================

    public boolean isInputFieldDisplayed() {
        return isDisplayed(inputValue, 3);
    }

    public boolean isInputFieldEnabled() {
        return isEnabled(inputValue);
    }

    // ==================== Navigation to Other Tabs ====================

    public ListPage navigateToListTab() {
        LoggerManager.step("Navigating to List tab");
        click(listTab);
        WaitHelper.sleep(1000);
        return new ListPage(driver);
    }

    public ButtonsPage navigateToButtonsTab() {
        LoggerManager.step("Navigating to Buttons tab");
        click(buttonsTab);
        WaitHelper.sleep(1000);
        return new ButtonsPage(driver);
    }

    public SwitchesPage navigateToSwitchesTab() {
        LoggerManager.step("Navigating to Switches tab");
        click(switchesTab);
        WaitHelper.sleep(1000);
        return new SwitchesPage(driver);
    }
}

