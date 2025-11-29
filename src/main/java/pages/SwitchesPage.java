package pages;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import base.BasePage;

/**
 * Switches Tab Page Object
 * Supports both Android and iOS platforms
 * Design Pattern: Page Object Model
 */
public class SwitchesPage extends BasePage {

    // ==================== Locators ====================
    
    private final By switch1 = By.id("com.example.trusttest:id/switch1");
    private final By switch2 = By.id("com.example.trusttest:id/switch2");
    private final By switch3 = By.id("com.example.trusttest:id/switch3");
    private final By saveButton = By.id("com.example.trusttest:id/saveButton");
    private final By saveStateText = By.id("com.example.trusttest:id/saveStateText");
    
    // Tab locators for navigation
    private final By listTab = By.xpath("//android.widget.LinearLayout[@content-desc='List']");
    private final By buttonsTab = By.xpath("//android.widget.LinearLayout[@content-desc='Buttons']");
    private final By inputTab = By.xpath("//android.widget.LinearLayout[@content-desc='Input']");

    // ==================== Constructor ====================
    
    public SwitchesPage(AppiumDriver driver) {
        super(driver);
    }

    // ==================== Page Verification ====================

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(switch1, 5);
    }

    // ==================== Switch Operations ====================

    public void toggleSwitch1() {
        LoggerManager.step("Toggling Switch 1");
        click(switch1);
    }

    public void toggleSwitch2() {
        LoggerManager.step("Toggling Switch 2");
        click(switch2);
    }

    public void toggleSwitch3() {
        LoggerManager.step("Toggling Switch 3");
        click(switch3);
    }

    public void clickSaveButton() {
        LoggerManager.step("Clicking Save button");
        click(saveButton);
        WaitHelper.sleep(500);
    }

    public void turnOnAllSwitches() {
        LoggerManager.step("Turning ON all switches");
        if (!isSwitch1On()) toggleSwitch1();
        if (!isSwitch2On()) toggleSwitch2();
        if (!isSwitch3On()) toggleSwitch3();
    }

    public void turnOffAllSwitches() {
        LoggerManager.step("Turning OFF all switches");
        if (isSwitch1On()) toggleSwitch1();
        if (isSwitch2On()) toggleSwitch2();
        if (isSwitch3On()) toggleSwitch3();
    }

    public boolean isSwitch1On() {
        return "true".equals(getAttribute(switch1, "checked"));
    }

    public boolean isSwitch2On() {
        return "true".equals(getAttribute(switch2, "checked"));
    }

    public boolean isSwitch3On() {
        return "true".equals(getAttribute(switch3, "checked"));
    }

    public String getSaveStateText() {
        return getText(saveStateText);
    }

    public boolean isSwitch1Displayed() {
        return isDisplayed(switch1, 3);
    }

    public boolean isSwitch2Displayed() {
        return isDisplayed(switch2, 3);
    }

    public boolean isSwitch3Displayed() {
        return isDisplayed(switch3, 3);
    }

    public boolean isSaveButtonDisplayed() {
        return isDisplayed(saveButton, 3);
    }

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

    public InputPage navigateToInputTab() {
        LoggerManager.step("Navigating to Input tab");
        click(inputTab);
        WaitHelper.sleep(1000);
        return new InputPage(driver);
    }
}

