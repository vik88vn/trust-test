package base;

import core.LoggerManager;
import core.WaitHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ScreenshotUtils;

import java.util.List;

/**
 * Base Page class implementing Page Object Model pattern
 * All page objects should extend this class
 * Supports both Android and iOS platforms
 */
public abstract class BasePage {

    protected AppiumDriver driver;
    protected WaitHelper waitHelper;
    protected ScreenshotUtils screenshotUtil;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.screenshotUtil = new ScreenshotUtils(driver);
        LoggerManager.debug("Initialized " + this.getClass().getSimpleName());
    }

    // ==================== Element Operations ====================

    protected WebElement find(By locator) {
        LoggerManager.debug("Finding element: " + locator);
        return waitHelper.waitForVisibility(locator);
    }

    protected List<WebElement> findAll(By locator) {
        LoggerManager.debug("Finding elements: " + locator);
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        LoggerManager.step("Clicking element: " + locator);
        waitHelper.waitForClickable(locator).click();
    }

    protected void clickByText(String text) {
        By locator = By.xpath("//*[@text='" + text + "']");
        click(locator);
    }

    protected void type(By locator, String text) {
        LoggerManager.step("Entering text into: " + locator);
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        LoggerManager.debug("Getting text from: " + locator);
        return find(locator).getText();
    }

    protected void clear(By locator) {
        LoggerManager.step("Clearing text from: " + locator);
        find(locator).clear();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitHelper.waitForVisibility(locator, 3).isDisplayed();
        } catch (Exception e) {
            LoggerManager.debug("Element not displayed: " + locator);
            return false;
        }
    }

    protected boolean isDisplayed(By locator, int timeout) {
        try {
            return waitHelper.waitForVisibility(locator, timeout).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return find(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isSelected(By locator) {
        try {
            return find(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    // ==================== Wait Operations ====================

    protected WebElement waitForVisibility(By locator) {
        return waitHelper.waitForVisibility(locator);
    }

    protected WebElement waitForClickable(By locator) {
        return waitHelper.waitForClickable(locator);
    }

    protected boolean waitForInvisibility(By locator) {
        return waitHelper.waitForInvisibility(locator);
    }

    protected void waitForText(String text) {
        By locator = By.xpath("//*[@text='" + text + "']");
        waitHelper.waitForVisibility(locator);
    }

    // ==================== Device Operations ====================

    protected void hideKeyboard() {
        try {
            driver.executeScript("mobile: hideKeyboard");
            LoggerManager.debug("Keyboard hidden");
        } catch (Exception e) {
            LoggerManager.debug("Keyboard not visible or already hidden");
        }
    }

    protected void pressBack() {
        LoggerManager.step("Pressing back button");
        driver.navigate().back();
    }

    // ==================== Screenshot Operations ====================

    protected String takeScreenshot(String name) {
        return screenshotUtil.takeScreenshot(name);
    }

    protected String captureStep(String stepName) {
        return screenshotUtil.captureStep(stepName);
    }

    /**
     * Verify page is loaded - to be implemented by subclasses
     */
    public abstract boolean isPageLoaded();
}

