package pages;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import base.BasePage;

/**
 * Buttons Tab Page Object
 * Supports both Android and iOS platforms
 * Design Pattern: Page Object Model
 */
public class ButtonsPage extends BasePage {

    // ==================== Locators ====================
    
    private final By button1 = By.id("com.example.trusttest:id/button1");
    private final By button2 = By.id("com.example.trusttest:id/button2");
    private final By button3 = By.id("com.example.trusttest:id/button3");
    private final By resetButton = By.id("com.example.trusttest:id/resetButton");
    
    // Tab locators for navigation
    private final By listTab = By.xpath("//android.widget.LinearLayout[@content-desc='List']");
    private final By switchesTab = By.xpath("//android.widget.LinearLayout[@content-desc='Switches']");
    private final By inputTab = By.xpath("//android.widget.LinearLayout[@content-desc='Input']");

    // ==================== Constructor ====================
    
    public ButtonsPage(AppiumDriver driver) {
        super(driver);
    }

    // ==================== Page Verification ====================

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(button1, 5);
    }

    // ==================== Button Operations ====================

    public void clickButton1() {
        LoggerManager.step("Clicking Button 1");
        click(button1);
    }

    public void clickButton2() {
        LoggerManager.step("Clicking Button 2");
        click(button2);
    }

    public void clickButton3() {
        LoggerManager.step("Clicking Button 3");
        click(button3);
    }

    public void clickResetButton() {
        LoggerManager.step("Clicking Reset button");
        click(resetButton);
    }

    public void clickAllButtons() {
        LoggerManager.step("Clicking all buttons");
        clickButton1();
        clickButton2();
        clickButton3();
    }

    // ==================== Verification Methods ====================

    public boolean isButton1Displayed() {
        return isDisplayed(button1, 3);
    }

    public boolean isButton2Displayed() {
        return isDisplayed(button2, 3);
    }

    public boolean isButton3Displayed() {
        return isDisplayed(button3, 3);
    }

    public boolean isResetButtonDisplayed() {
        return isDisplayed(resetButton, 3);
    }

    public String getButton1Text() {
        return getText(button1);
    }

    public String getButton2Text() {
        return getText(button2);
    }

    public String getButton3Text() {
        return getText(button3);
    }

    // ==================== Navigation to Other Tabs ====================

    public ListPage navigateToListTab() {
        LoggerManager.step("Navigating to List tab");
        click(listTab);
        WaitHelper.sleep(1000);
        return new ListPage(driver);
    }

    public SwitchesPage navigateToSwitchesTab() {
        LoggerManager.step("Navigating to Switches tab");
        click(switchesTab);
        WaitHelper.sleep(1000);
        return new SwitchesPage(driver);
    }

    public InputPage navigateToInputTab() {
        LoggerManager.step("Navigating to Input tab");
        click(inputTab);
        WaitHelper.sleep(1000);
        return new InputPage(driver);
    }
}

