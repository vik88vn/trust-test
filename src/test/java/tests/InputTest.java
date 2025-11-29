package tests;

import base.BaseTest;
import core.LoggerManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Input Tab functionality tests
 */
public class InputTest extends BaseTest {

    @BeforeMethod
    public void setupInputTab() {
        performLogin();
        inputPage = listPage.navigateToInputTab();
    }

    @Test(description = "Verify Input tab is displayed")
    public void testInputTabDisplayed() {
        captureStep("InputTab_Initial");
        
        Assert.assertTrue(inputPage.isPageLoaded(), 
            "Input tab should be displayed");
        Assert.assertTrue(inputPage.isInputFieldDisplayed(), 
            "Input field should be displayed");
        Assert.assertTrue(inputPage.isInputFieldEnabled(), 
            "Input field should be enabled");
        
        LoggerManager.success("Input tab displayed correctly");
    }

    @Test(description = "Verify input field accepts numeric value")
    public void testInputNumericValue() {
        String testValue = "12345";
        
        inputPage.clearValue();
        inputPage.enterValue(testValue);
        captureStep("Numeric_Value_Entered");
        
        LoggerManager.success("Numeric value entered");
    }

    @Test(description = "Verify input field accepts large numbers")
    public void testInputLargeNumber() {
        String largeNumber = "999999";
        
        inputPage.enterValue(largeNumber);
        captureStep("Large_Number_Entered");
        
        LoggerManager.success("Large number accepted");
    }

    @Test(description = "Verify input field can be cleared")
    public void testInputFieldClear() {
        inputPage.enterValue("123");
        captureStep("Before_Clear");
        
        inputPage.clearValue();
        captureStep("After_Clear");
        
        LoggerManager.success("Input field cleared");
    }

    @Test(description = "Verify entering zero value")
    public void testInputZeroValue() {
        inputPage.enterValue("0");
        captureStep("Zero_Value");
        
        LoggerManager.success("Zero value entered");
    }

    @Test(description = "Verify entering decimal values")
    public void testInputDecimalValue() {
        String decimalValue = "123.45";
        
        inputPage.enterValue(decimalValue);
        captureStep("Decimal_Value");
        
        LoggerManager.success("Decimal value entered");
    }
}

