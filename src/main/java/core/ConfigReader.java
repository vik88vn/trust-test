package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class to read configuration from properties file
 * Design Pattern: Singleton
 */
public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties;

    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            LoggerManager.info("Configuration loaded successfully");
        } catch (IOException e) {
            LoggerManager.error("Failed to load configuration: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LoggerManager.warn("Property not found: " + key);
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Mobile options settings methods
    public String getAppiumUrl() {
        return getProperty("appium.url", "http://127.0.0.1:4723");
    }

    public String getPlatformName() {
        return getProperty("platform.name", "Android");
    }

    public String getAutomationName() {
        return getProperty("automation.name", "UiAutomator2");
    }

    public String getDeviceName() {
        return getProperty("device.name", "");
    }

    public String getPlatformVersion() {
        return getProperty("platform.version", "");
    }

    public String getAppPath() {
        String path = getProperty("app.path", "apps/trust_test.apk");
        return System.getProperty("user.dir") + "/" + path;
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }

    public boolean getAutoGrantPermissions() {
        return Boolean.parseBoolean(getProperty("auto.grant.permissions", "true"));
    }

    public boolean getNoReset() {
        return Boolean.parseBoolean(getProperty("no.reset", "false"));
    }

    public boolean getFullReset() {
        return Boolean.parseBoolean(getProperty("full.reset", "false"));
    }
}
