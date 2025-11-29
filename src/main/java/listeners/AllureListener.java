package listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.appium.java_client.AppiumDriver;
import core.DriverFactory;
import core.LoggerManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Allure Listener to capture screenshots and attach to report
 * Best Practice: Automatically captures screenshots on test failure
 */
public class AllureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        LoggerManager.info("⚡ Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerManager.success("✅ Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerManager.error("❌ Test failed: " + result.getMethod().getMethodName());
        
        // Capture screenshot on failure
        AppiumDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            saveScreenshot(driver);
            savePageSource(driver);
        }
        
        // Attach exception details
        saveTextLog("Error Message: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerManager.info("⏭️ Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        LoggerManager.info("🚀 Starting Test Suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerManager.info("🏁 Finished Test Suite: " + context.getName());
        
        // Attach log file to Allure report
        attachLogFile();
    }

    /**
     * Attach screenshot to Allure report
     */
    @Attachment(value = "Screenshot on Failure", type = "image/png")
    private byte[] saveScreenshot(AppiumDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            LoggerManager.error("Failed to capture screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Attach page source to Allure report
     */
    @Attachment(value = "Page Source", type = "text/xml")
    private String savePageSource(AppiumDriver driver) {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            LoggerManager.error("Failed to capture page source: " + e.getMessage());
            return "Unable to capture page source";
        }
    }

    /**
     * Attach text log to Allure report
     */
    @Attachment(value = "Test Log", type = "text/plain")
    private String saveTextLog(String message) {
        return message;
    }

    /**
     * Attach current log file to Allure report
     */
    private void attachLogFile() {
        String logFilePath = LoggerManager.getCurrentLogFile();
        if (logFilePath != null) {
            try {
                byte[] logContent = Files.readAllBytes(Paths.get(logFilePath));
                Allure.addAttachment("Test Execution Log", "text/plain", 
                    new String(logContent), ".log");
                LoggerManager.info("Log file attached to Allure report");
            } catch (IOException e) {
                LoggerManager.error("Failed to attach log file: " + e.getMessage());
            }
        }
    }
}

