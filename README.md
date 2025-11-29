# Trust Mobile Automation Framework

Clean and lightweight Appium automation framework for Android mobile testing.

**Stack:** Java 11 | TestNG | Appium | Allure Report | Maven

---

## 🏗️ Architecture

```
trust-test/
├── src/main/java/
│   ├── base/
│   │   └── BasePage.java              # Base page with common methods
│   ├── core/                          # Core framework components
│   │   ├── ConfigReader.java          # Configuration management
│   │   ├── DriverFactory.java         # Driver initialization
│   │   ├── LoggerManager.java         # File + Console logging
│   │   ├── Platform.java              # Platform enum
│   │   └── WaitHelper.java            # Wait utilities
│   ├── listeners/                     # TestNG listeners
│   │   ├── AllureListener.java        # Allure integration
│   │   └── LoggingListener.java       # Log file management
│   ├── pages/                         # Page Object Model
│   │   ├── LoginPage.java
│   │   ├── ListPage.java
│   │   ├── ButtonsPage.java
│   │   ├── SwitchesPage.java
│   │   └── InputPage.java
│   └── utils/                         # Utilities
│       ├── AllureUtils.java           # Allure helpers
│       ├── LogUtils.java              # Log management
│       └── ScreenshotUtils.java       # Screenshot capture
│
├── src/test/java/
│   ├── base/
│   │   └── BaseTest.java              # Base test class
│   └── tests/                         # Test suites
│       ├── LoginTest.java
│       ├── ListTest.java
│       ├── ButtonsTest.java
│       ├── SwitchesTest.java
│       └── InputTest.java
│
├── src/test/resources/
│   ├── config.properties              # Test configuration
│   ├── categories.json                # Allure categories
│   └── environment.properties         # Environment info
│
├── logs/                              # Test execution logs
├── screenshots/                       # Test screenshots
├── apps/                              # Test APK files
├── testng.xml                         # TestNG suite config
└── pom.xml                            # Maven dependencies
```

---

## 🎯 Design Patterns

| Pattern | Usage | Benefit |
|---------|-------|---------|
| **Page Object Model** | All page classes | Maintainable, reusable |
| **Singleton** | ConfigReader, DriverFactory | Single instance |
| **Factory** | DriverFactory | Dynamic driver creation |
| **Facade** | LoggerManager | Simple logging interface |

---

## 📋 Prerequisites

- **Java JDK 11+** - [Download](https://adoptium.net/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **Node.js 16+** - [Download](https://nodejs.org/)
- **Android Studio** - [Download](https://developer.android.com/studio)
- **Appium 2.x** - Install via npm
- **Allure CLI** (optional) - For viewing reports

---

## ⚙️ Installation & Setup

### 1️⃣ Install Node.js & Appium

```bash
# Install Appium server
npm install -g appium

# Install UiAutomator2 driver
appium driver install uiautomator2

# Verify installation
appium --version
appium driver list
```

### 2️⃣ Configure Android SDK

```bash
# Add to ~/.zshrc or ~/.bashrc
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools

# Apply changes
source ~/.zshrc  # or source ~/.bashrc

# Verify
adb --version
```

### 3️⃣ Setup Android Emulator

```bash
# Open Android Studio > Device Manager > Create Virtual Device
# Or use command line:

# List installed system images
sdkmanager --list | grep system-images

# List emulators
emulator -list-avds

# Start emulator
emulator -avd Pixel_6 &
```

### 4️⃣ Clone & Install Project

```bash
# Clone repository
git clone <repository-url>
cd trust-test

# Install dependencies
mvn clean install -DskipTests

# Verify build
mvn compile
```

### 5️⃣ Install Allure CLI (Optional)

```bash
# MacOS
brew install allure

# Linux (manual)
# Download from: https://github.com/allure-framework/allure2/releases
# Extract and add to PATH

# Verify
allure --version
```

---

## 🚀 Running Tests

### Quick Start

```bash
# 1. Start Appium server
appium

# 2. Start Android emulator
emulator -avd Pixel_6

# 3. Run tests 
cd trust-test
mvn clean test
```

### Run Specific Tests

```bash
# Run all tests
mvn clean test

# Run single test class
mvn test -Dtest=LoginTest

# Run single test method
mvn test -Dtest=LoginTest#testLoginWithValidCredentials

# Run with TestNG XML
mvn test -DsuiteXmlFile=testng.xml
```

### Configuration

Edit to your device information `src/test/resources/config.properties`:

```properties
# Appium server
appium.url=http://127.0.0.1:4723

# Device settings (leave empty for auto-detect)
device.name=
platform.name=Android
platform.version=
automation.name=UiAutomator2

# App
app.path=apps/trust_test.apk

# Timeouts (seconds)
implicit.wait=10
explicit.wait=15
```

---

## 📊 Test Reports

### 1. Allure Report 

```bash
# Generate and view report (auto-opens browser)
mvn allure:serve

# Or generate static report
mvn allure:report
allure open target/allure-report
```

**Allure Features:**
- 📸 Auto-capture screenshots on failure
- 📝 Detailed test steps
- 📊 Graphs and statistics
- 🏷️ Test categorization (Epic/Feature/Story)
- 📁 Log files attached
- 🎯 Severity levels
- ⏱️ Execution timeline
---

## 🐛 Debugging Support
### Screenshot
**Location:** `screenshots/`
### Logs
**Auto-generated log files:**
- **Location:** `logs/test_execution_YYYYMMDD_HHMMSS.log` all steps will be logged.
- **Format:** Timestamped entries with log levels.
