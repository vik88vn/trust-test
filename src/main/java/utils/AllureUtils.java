package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

/**
 * Allure Utilities for attaching screenshots and logs
 * Best Practice: Simple and focused on actual usage
 */
public class AllureUtils {

    /**
     * Attach screenshot to Allure report (used in BaseTest)
     */
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(AppiumDriver driver, String name) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Attach screenshot using Allure API (used in BaseTest)
     */
    public static void saveScreenshot(AppiumDriver driver, String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot: " + e.getMessage());
        }
    }

    /**
     * Attach text to Allure report (used in BaseTest)
     */
    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String content) {
        return content;
    }

    /**
     * Attach page source to Allure report (used in BaseTest)
     */
    @Attachment(value = "{name}", type = "text/xml")
    public static String attachPageSource(AppiumDriver driver, String name) {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            return "Unable to capture page source";
        }
    }

    /**
     * Add parameter to Allure report (used in tests)
     */
    public static void addParameter(String name, String value) {
        Allure.parameter(name, value);
    }
}

