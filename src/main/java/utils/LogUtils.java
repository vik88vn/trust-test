package utils;

import core.LoggerManager;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for managing log files
 * Best Practice: Auto cleanup old logs, archive logs, etc.
 */
public class LogUtils {

    private static final String LOG_DIR = "logs";
    private static final int MAX_LOG_FILES = 10; // Keep only last 10 log files

    /**
     * Clean up old log files, keeping only the most recent ones
     */
    public static void cleanupOldLogs() {
        cleanupOldLogs(MAX_LOG_FILES);
    }

    /**
     * Clean up old log files
     * @param keepCount Number of recent log files to keep
     */
    public static void cleanupOldLogs(int keepCount) {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists() || !logDir.isDirectory()) {
            return;
        }

        File[] logFiles = logDir.listFiles((dir, name) -> name.endsWith(".log"));
        if (logFiles == null || logFiles.length <= keepCount) {
            return;
        }

        // Sort by last modified date (newest first)
        Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified).reversed());

        // Delete old files
        int deletedCount = 0;
        for (int i = keepCount; i < logFiles.length; i++) {
            if (logFiles[i].delete()) {
                deletedCount++;
            }
        }

        if (deletedCount > 0) {
            LoggerManager.info("Cleaned up " + deletedCount + " old log files");
        }
    }

    /**
     * Get all log files sorted by date (newest first)
     */
    public static File[] getLogFiles() {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists() || !logDir.isDirectory()) {
            return new File[0];
        }

        File[] logFiles = logDir.listFiles((dir, name) -> name.endsWith(".log"));
        if (logFiles == null) {
            return new File[0];
        }

        Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified).reversed());
        return logFiles;
    }

    /**
     * Get the most recent log file
     */
    public static File getMostRecentLogFile() {
        File[] logFiles = getLogFiles();
        return logFiles.length > 0 ? logFiles[0] : null;
    }

    /**
     * Archive old logs to a zip file
     */
    public static void archiveLogs() {
        LoggerManager.info("Log archiving not yet implemented");
    }

    /**
     * Get total size of all log files
     */
    public static long getTotalLogSize() {
        File[] logFiles = getLogFiles();
        long totalSize = 0;
        for (File file : logFiles) {
            totalSize += file.length();
        }
        return totalSize;
    }

    /**
     * Format file size to human readable format
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Print log files summary
     */
    public static void printLogSummary() {
        File[] logFiles = getLogFiles();
        long totalSize = getTotalLogSize();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("LOG FILES SUMMARY");
        System.out.println("=".repeat(80));
        System.out.println("Total log files: " + logFiles.length);
        System.out.println("Total size: " + formatFileSize(totalSize));
        System.out.println("-".repeat(80));

        if (logFiles.length > 0) {
            System.out.println("Recent log files:");
            int count = Math.min(5, logFiles.length);
            for (int i = 0; i < count; i++) {
                File file = logFiles[i];
                System.out.printf("  %d. %s (%s)%n", 
                    i + 1, 
                    file.getName(), 
                    formatFileSize(file.length()));
            }
        }
        System.out.println("=".repeat(80) + "\n");
    }
}

