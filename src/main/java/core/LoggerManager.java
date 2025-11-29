package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger Manager for consistent logging across framework
 * Design Pattern: Facade
 * Features: Console output + File logging
 */
public class LoggerManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static PrintWriter logFileWriter;
    private static String currentLogFile;
    private static final String LOG_DIR = "logs";
    private static boolean fileLoggingEnabled = true;

    // ThreadLocal để track test name cho mỗi thread (parallel execution)
    private static ThreadLocal<String> currentTestName = new ThreadLocal<>();

    static {
        initializeLogDirectory();
    }

    /**
     * Initialize log directory
     */
    private static void initializeLogDirectory() {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    /**
     * Start logging for a test session
     */
    public static void startSession() {
        if (!fileLoggingEnabled) return;

        String timestamp = LocalDateTime.now().format(fileFormatter);
        currentLogFile = LOG_DIR + "/test_execution_" + timestamp + ".log";
        
        try {
            logFileWriter = new PrintWriter(new FileWriter(currentLogFile, true), true);
            String header = "=".repeat(80) + "\n" +
                          "TEST EXECUTION SESSION STARTED\n" +
                          "Timestamp: " + getTimestamp() + "\n" +
                          "=".repeat(80);
            logFileWriter.println(header);
            logFileWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to initialize log file: " + e.getMessage());
            fileLoggingEnabled = false;
        }
    }

    /**
     * End logging session and close file
     */
    public static void endSession() {
        if (logFileWriter != null) {
            String footer = "\n" + "=".repeat(80) + "\n" +
                          "TEST EXECUTION SESSION ENDED\n" +
                          "Timestamp: " + getTimestamp() + "\n" +
                          "Log file: " + currentLogFile + "\n" +
                          "=".repeat(80);
            logFileWriter.println(footer);
            logFileWriter.close();
            logFileWriter = null;
        }
    }

    /**
     * Get current log file path
     */
    public static String getCurrentLogFile() {
        return currentLogFile;
    }

    /**
     * Write to both console and file
     */
    private static void log(String consoleMessage, String fileMessage) {
        System.out.println(consoleMessage);
        
        if (fileLoggingEnabled && logFileWriter != null) {
            logFileWriter.println(fileMessage);
            logFileWriter.flush();
        }
    }

    private static String getTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    public static void info(String message) {
        String timestamp = getTimestamp();
        String consoleMsg = ANSI_BLUE + "[INFO]  " + timestamp + " - " + message + ANSI_RESET;
        String fileMsg = "[INFO]  " + timestamp + " - " + message;
        log(consoleMsg, fileMsg);
    }

    public static void debug(String message) {
        String timestamp = getTimestamp();
        String msg = "[DEBUG] " + timestamp + " - " + message;
        log(msg, msg);
    }

    public static void warn(String message) {
        String timestamp = getTimestamp();
        String consoleMsg = ANSI_YELLOW + "[WARN]  " + timestamp + " - " + message + ANSI_RESET;
        String fileMsg = "[WARN]  " + timestamp + " - " + message;
        log(consoleMsg, fileMsg);
    }

    public static void error(String message) {
        String timestamp = getTimestamp();
        String consoleMsg = ANSI_RED + "[ERROR] " + timestamp + " - " + message + ANSI_RESET;
        String fileMsg = "[ERROR] " + timestamp + " - " + message;
        
        System.err.println(consoleMsg);
        if (fileLoggingEnabled && logFileWriter != null) {
            logFileWriter.println(fileMsg);
            logFileWriter.flush();
        }
    }

    public static void success(String message) {
        String timestamp = getTimestamp();
        String consoleMsg = ANSI_GREEN + "[✓]     " + timestamp + " - " + message + ANSI_RESET;
        String fileMsg = "[PASS]  " + timestamp + " - " + message;
        log(consoleMsg, fileMsg);
    }

    public static void step(String stepDescription) {
        String timestamp = getTimestamp();
        String consoleMsg = ANSI_BLUE + "[STEP]  " + timestamp + " - " + stepDescription + ANSI_RESET;
        String fileMsg = "[STEP]  " + timestamp + " - " + stepDescription;
        log(consoleMsg, fileMsg);
    }

    public static void testStart(String testName) {
        currentTestName.set(testName);
        String timestamp = getTimestamp();
        
        String consoleSeparator = "\n" + ANSI_GREEN + "========================================" + ANSI_RESET;
        String consoleMsg = consoleSeparator + "\n" +
                          ANSI_GREEN + "TEST STARTED: " + testName + ANSI_RESET + "\n" +
                          ANSI_GREEN + "========================================" + ANSI_RESET;
        
        String fileSeparator = "\n" + "=".repeat(80);
        String fileMsg = fileSeparator + "\n" +
                        "TEST STARTED: " + testName + "\n" +
                        "Timestamp: " + timestamp + "\n" +
                        "=".repeat(80);
        
        log(consoleMsg, fileMsg);
    }

    public static void testEnd(String testName, boolean passed) {
        currentTestName.remove();
        String timestamp = getTimestamp();
        String status = passed ? "PASSED" : "FAILED";
        
        String consoleStatus = passed ? ANSI_GREEN + "PASSED" : ANSI_RED + "FAILED";
        String consoleSeparator = ANSI_GREEN + "========================================" + ANSI_RESET;
        String consoleMsg = consoleSeparator + "\n" +
                          consoleStatus + ": " + testName + ANSI_RESET + "\n" +
                          consoleSeparator + "\n";
        
        String fileSeparator = "=".repeat(80);
        String fileMsg = fileSeparator + "\n" +
                        "TEST " + status + ": " + testName + "\n" +
                        "Timestamp: " + timestamp + "\n" +
                        fileSeparator + "\n";
        
        log(consoleMsg, fileMsg);
    }

    /**
     * Enable/disable file logging
     */
    public static void setFileLoggingEnabled(boolean enabled) {
        fileLoggingEnabled = enabled;
    }

    /**
     * Check if file logging is enabled
     */
    public static boolean isFileLoggingEnabled() {
        return fileLoggingEnabled;
    }
}
