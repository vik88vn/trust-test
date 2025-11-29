package listeners;

import core.LoggerManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.LogUtils;

/**
 * TestNG Suite Listener to manage log file lifecycle
 * Automatically starts/stops log file for each test suite execution
 */
public class LoggingListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // Clean up old logs before starting new session
        LogUtils.cleanupOldLogs(10);
        
        // Start logging session when suite starts
        LoggerManager.startSession();
        LoggerManager.info("=== Test Suite Started: " + suite.getName() + " ===");
    }

    @Override
    public void onFinish(ISuite suite) {
        // End logging session when suite finishes
        LoggerManager.info("=== Test Suite Finished: " + suite.getName() + " ===");
        LoggerManager.info("Total Tests: " + suite.getAllMethods().size());
        
        String logFile = LoggerManager.getCurrentLogFile();
        if (logFile != null) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("📝 Test logs saved to: " + logFile);
            System.out.println("=".repeat(80) + "\n");
        }
        
        LoggerManager.endSession();
        
        // Print log summary
        LogUtils.printLogSummary();
    }
}

