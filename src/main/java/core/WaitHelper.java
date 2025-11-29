package core;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Helper class for explicit waits
 * Supports both Android and iOS platforms
 * Design Pattern: Fluent Interface
 */
public class WaitHelper {
    
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final int defaultTimeout;
    
    public WaitHelper(AppiumDriver driver) {
        this.driver = driver;
        this.defaultTimeout = ConfigReader.getInstance().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));
    }
    
    public WaitHelper(AppiumDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.defaultTimeout = timeoutSeconds;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }
    
    /**
     * Wait for element to be visible
     */
    public WebElement waitForVisibility(By locator) {
        LoggerManager.debug("Waiting for element to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be visible with custom timeout
     */
    public WebElement waitForVisibility(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    public WebElement waitForClickable(By locator) {
        LoggerManager.debug("Waiting for element to be clickable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be clickable with custom timeout
     */
    public WebElement waitForClickable(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be invisible
     */
    public boolean waitForInvisibility(By locator) {
        LoggerManager.debug("Waiting for element to be invisible: " + locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be invisible with custom timeout
     */
    public boolean waitForInvisibility(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextPresent(By locator, String text) {
        LoggerManager.debug("Waiting for text '" + text + "' in element: " + locator);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    /**
     * Wait for element to be present in DOM
     */
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait for alert to be present
     */
    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }
    
    /**
     * Static sleep method
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerManager.warn("Sleep interrupted");
        }
    }
    
    /**
     * Fluent wait with custom polling
     */
    public WebElement fluentWait(By locator, int timeoutSeconds, int pollingMillis) {
        WebDriverWait fluentWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        fluentWait.pollingEvery(Duration.ofMillis(pollingMillis));
        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

