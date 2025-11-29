package utils;

import core.LoggerManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for taking screenshots
 * Supports both Android and iOS platforms
 * Design Pattern: Utility
 */
public class ScreenshotUtils {

    private final AppiumDriver driver;
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public ScreenshotUtils(AppiumDriver driver) {
        this.driver = driver;
        createScreenshotDirectory();
    }

    /**
     * Create screenshots directory if it doesn't exist
     */
    private void createScreenshotDirectory() {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            LoggerManager.info("Created screenshots directory: " + SCREENSHOT_DIR);
        }
    }

    /**
     * Take screenshot with auto-generated timestamp name
     */
    public String takeScreenshot() {
        String timestamp = dateFormat.format(new Date());
        return takeScreenshot("screenshot_" + timestamp);
    }

    /**
     * Take screenshot with custom name
     */
    public String takeScreenshot(String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = screenshotName + ".png";
            Path destPath = Paths.get(SCREENSHOT_DIR, fileName);
            Files.copy(srcFile.toPath(), destPath);
            
            String absolutePath = destPath.toAbsolutePath().toString();
            LoggerManager.info("📸 Screenshot saved: " + absolutePath);
            return absolutePath;
        } catch (IOException e) {
            LoggerManager.error("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Take screenshot for a test step
     */
    public String captureStep(String stepName) {
        String timestamp = dateFormat.format(new Date());
        String fileName = stepName.replaceAll("\\s+", "_") + "_" + timestamp;
        return takeScreenshot(fileName);
    }
    
    /**
     * Take screenshot on failure
     */
    public String captureFailure(String testName) {
        String timestamp = dateFormat.format(new Date());
        String fileName = "FAILED_" + testName + "_" + timestamp;
        LoggerManager.error("Test failed, capturing screenshot...");
        return takeScreenshot(fileName);
    }

    /**
     * Take screenshot and return as Base64 string
     */
    public String takeScreenshotAsBase64() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}

