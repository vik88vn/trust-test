package pages;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.BasePage;

import java.util.List;

/**
 * List Tab Page Object
 * Supports both Android and iOS platforms
 * Design Pattern: Page Object Model
 */
public class ListPage extends BasePage {

    // ==================== Locators ====================
    
    private final By testTitle = By.xpath("//android.widget.TextView[@text='Test']");
    private final By instructionsText = By.id("com.example.trusttest:id/instructionsText");
    private final By listItems = By.id("android:id/text1");
    
    private final By buttonsTab = By.xpath("//android.widget.LinearLayout[@content-desc='Buttons']");
    private final By switchesTab = By.xpath("//android.widget.LinearLayout[@content-desc='Switches']");
    private final By inputTab = By.xpath("//android.widget.LinearLayout[@content-desc='Input']");
    
    private final By navigateUpButton = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
    
    public ListPage(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(testTitle, 5) && isDisplayed(instructionsText, 5);
    }

    public String getInstructionsText() {
        return getText(instructionsText);
    }

    public List<WebElement> getListItems() {
        return findAll(listItems);
    }

    public int getListItemCount() {
        return getListItems().size();
    }

    public void clickListItem(String itemText) {
        LoggerManager.step("Clicking list item: " + itemText);
        click(By.xpath("//android.widget.TextView[@text='" + itemText + "']"));
    }

    public void clickListItemByIndex(int index) {
        LoggerManager.step("Clicking list item at index: " + index);
        List<WebElement> items = getListItems();
        if (index < items.size()) {
            items.get(index).click();
        } else {
            LoggerManager.warn("Index out of bounds: " + index);
        }
    }

    public boolean isListItemDisplayed(String itemText) {
        return isDisplayed(By.xpath("//android.widget.TextView[@text='" + itemText + "']"), 3);
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

    public InputPage navigateToInputTab() {
        LoggerManager.step("Navigating to Input tab");
        click(inputTab);
        WaitHelper.sleep(1000);
        return new InputPage(driver);
    }

    public void clickNavigateUp() {
        LoggerManager.step("Clicking Navigate Up button");
        click(navigateUpButton);
    }
}

