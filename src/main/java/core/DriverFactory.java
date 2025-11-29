package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Factory class to create and manage AppiumDriver instance
 * Supports both Android and iOS platforms
 * Design Pattern: Singleton + Factory + Strategy
 */
public class DriverFactory {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static ConfigReader config = ConfigReader.getInstance();

    private DriverFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Get driver instance - creates if not exists
     */
    public static AppiumDriver getDriver() {
        if (driver.get() == null) {
            Platform platform = getPlatformFromConfig();
            driver.set(createDriver(platform));
        }
        return driver.get();
    }

    /**
     * Get platform from configuration
     */
    private static Platform getPlatformFromConfig() {
        String platformName = config.getPlatformName().toUpperCase();
        try {
            return Platform.valueOf(platformName);
        } catch (IllegalArgumentException e) {
            LoggerManager.warn("Invalid platform: " + platformName + ", defaulting to ANDROID");
            return Platform.ANDROID;
        }
    }

    /**
     * Create driver based on platform
     * Strategy Pattern: Different strategy for each platform
     */
    private static AppiumDriver createDriver(Platform platform) {
        LoggerManager.info("Creating " + platform + " driver...");

        switch (platform) {
            case IOS:
                return createIOSDriver();
            case ANDROID:
            default:
                return createAndroidDriver();
        }
    }

    /**
     * Create Android driver with UiAutomator2
     */
    private static AndroidDriver createAndroidDriver() {
        LoggerManager.info("Initializing Android Driver with UiAutomator2...");

        UiAutomator2Options options = new UiAutomator2Options();

        // Platform settings
        options.setPlatformName(config.getPlatformName());
        options.setAutomationName(config.getAutomationName());

        // Device settings
        String deviceName = config.getDeviceName();
        if (!deviceName.isEmpty()) {
            options.setDeviceName(deviceName);
        }

        String platformVersion = config.getPlatformVersion();
        if (!platformVersion.isEmpty()) {
            options.setPlatformVersion(platformVersion);
        }

        // App settings
        String appPath = config.getAppPath();
        File appFile = new File(appPath);
        if (appFile.exists()) {
            options.setApp(appFile.getAbsolutePath());
            LoggerManager.info("App path: " + appPath);
        } else {
            LoggerManager.warn("App file not found at: " + appPath);
        }

        // Android-specific capabilities
        options.setCapability("appium:autoGrantPermissions", config.getAutoGrantPermissions());
        options.setCapability("appium:noReset", config.getNoReset());
        options.setCapability("appium:fullReset", config.getFullReset());
        options.setCapability("appium:newCommandTimeout", 300);

        try {
            AndroidDriver androidDriver = new AndroidDriver(
                    new URL(config.getAppiumUrl()),
                    options);

            // Set implicit wait
            androidDriver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

            LoggerManager.success("Android Driver created successfully");
            return androidDriver;

        } catch (MalformedURLException e) {
            LoggerManager.error("Invalid Appium URL: " + e.getMessage());
            throw new RuntimeException("Failed to create Android driver", e);
        }
    }

    /**
     * Create iOS driver with XCUITest
     */
    private static IOSDriver createIOSDriver() {
        LoggerManager.info("Initializing iOS Driver with XCUITest...");

        XCUITestOptions options = new XCUITestOptions();

        // Platform settings
        options.setPlatformName("iOS");
        options.setAutomationName("XCUITest");

        // Device settings
        String deviceName = config.getDeviceName();
        if (!deviceName.isEmpty()) {
            options.setDeviceName(deviceName);
        } else {
            options.setDeviceName("iPhone 14"); // Default
        }

        String platformVersion = config.getPlatformVersion();
        if (!platformVersion.isEmpty()) {
            options.setPlatformVersion(platformVersion);
        }

        // App settings
        String appPath = config.getAppPath();
        File appFile = new File(appPath);
        if (appFile.exists()) {
            options.setApp(appFile.getAbsolutePath());
            LoggerManager.info("App path: " + appPath);
        } else {
            LoggerManager.warn("App file not found at: " + appPath);
        }

        // iOS-specific capabilities
        options.setCapability("appium:autoAcceptAlerts", true);
        options.setCapability("appium:noReset", config.getNoReset());
        options.setCapability("appium:fullReset", config.getFullReset());
        options.setCapability("appium:newCommandTimeout", 300);

        try {
            IOSDriver iosDriver = new IOSDriver(
                    new URL(config.getAppiumUrl()), options);

            // Set implicit wait
            iosDriver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

            LoggerManager.success("iOS Driver created successfully");
            return iosDriver;

        } catch (MalformedURLException e) {
            LoggerManager.error("Invalid Appium URL: " + e.getMessage());
            throw new RuntimeException("Failed to create iOS driver", e);
        }
    }

    /**
     * Quit driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            LoggerManager.info("Quitting driver...");
            driver.get().quit();
            driver.remove();
            LoggerManager.success("Driver quit successfully");
        }
    }

    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }

    /**
     * Get current platform
     */
    public static Platform getCurrentPlatform() {
        return getPlatformFromConfig();
    }
}
