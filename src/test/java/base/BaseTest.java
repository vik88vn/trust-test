package base;

import core.DriverFactory;
import core.LoggerManager;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.*;
import utils.AllureUtils;
import utils.ScreenshotUtils;

import java.lang.reflect.Method;

/**
 * Base Test class for all test classes
 * Supports both Android and iOS platforms
 * Design Pattern: Template Method
 */
public class BaseTest {

    protected AppiumDriver driver;
    protected ScreenshotUtils screenshotUtil;
    
    // Page Objects
    protected LoginPage loginPage;
    protected ListPage listPage;
    protected ButtonsPage buttonsPage;
    protected SwitchesPage switchesPage;
    protected InputPage inputPage;

    @BeforeMethod
    public void setUp(Method method) {
        String testName = method.getName();
        LoggerManager.testStart(testName);
        
        // Initialize driver
        driver = DriverFactory.getDriver();
        screenshotUtil = new ScreenshotUtils(driver);
        
        // Add test info to Allure
        Allure.parameter("Test Name", testName);
        Allure.parameter("Platform", core.ConfigReader.getInstance().getPlatformName());
        
        // Initialize page objects
        initializePages();
        
        LoggerManager.success("Test setup completed");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        boolean passed = result.getStatus() == ITestResult.SUCCESS;
        
        // Take screenshot on failure and attach to Allure
        if (!passed) {
            screenshotUtil.captureFailure(testName);
            AllureUtils.attachScreenshot(driver, "Failure Screenshot");
            AllureUtils.attachPageSource(driver, "Page Source on Failure");
            
            // Attach exception details
            if (result.getThrowable() != null) {
                AllureUtils.attachText("Error Details", result.getThrowable().toString());
            }
        }
        
        // Quit driver
        DriverFactory.quitDriver();
        
        LoggerManager.testEnd(testName, passed);
    }

    /**
     * Initialize all page objects
     */
    private void initializePages() {
        loginPage = new LoginPage(driver);
    }

    /**
     * Initialize pages after login
     */
    protected void initializePagesAfterLogin() {
        listPage = new ListPage(driver);
        buttonsPage = new ButtonsPage(driver);
        switchesPage = new SwitchesPage(driver);
        inputPage = new InputPage(driver);
    }

    /**
     * Common login method
     */
    protected ListPage performLogin(String username, String password) {
        LoggerManager.info("Performing login...");
        Allure.step("Login with username: " + username);
        listPage = loginPage.login(username, password);
        initializePagesAfterLogin();
        return listPage;
    }

    /**
     * Common login with default credentials
     */
    protected ListPage performLogin() {
        return performLogin("admin", "password");
    }

    /**
     * Take screenshot helper with Allure integration
     */
    protected String captureStep(String stepName) {
        String path = screenshotUtil.captureStep(stepName);
        AllureUtils.saveScreenshot(driver, stepName);
        return path;
    }
}
