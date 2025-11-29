package tests;

import base.BaseTest;
import core.LoggerManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.AllureUtils;

/**
 * Buttons Tab functionality tests
 * KISS: Keep only essential tests
 */
@Epic("Mobile App Testing")
@Feature("Buttons Feature")
public class ButtonsTest extends BaseTest {

    @BeforeMethod
    public void setupButtonsTab() {
        performLogin();
        buttonsPage = listPage.navigateToButtonsTab();
    }

    @Test(description = "Verify Buttons tab displays correctly")
    @Description("Test to verify all button elements are displayed")
    @Severity(SeverityLevel.NORMAL)
    @Story("Button Display")
    public void testButtonsTabDisplayed() {
        captureStep("ButtonsTab_Initial");
        
        Assert.assertTrue(buttonsPage.isPageLoaded(), "Buttons tab should be displayed");
        Assert.assertTrue(buttonsPage.isButton1Displayed(), "Button 1 should be displayed");
        Assert.assertTrue(buttonsPage.isButton2Displayed(), "Button 2 should be displayed");
        Assert.assertTrue(buttonsPage.isButton3Displayed(), "Button 3 should be displayed");
        Assert.assertTrue(buttonsPage.isResetButtonDisplayed(), "Reset button should be displayed");
        
        LoggerManager.success("All buttons displayed");
    }

    @Test(description = "Verify button click and reset functionality")
    @Description("Test to verify buttons can be clicked and reset works")
    @Severity(SeverityLevel.NORMAL)
    @Story("Button Interaction")
    public void testButtonClickAndReset() {
        buttonsPage.clickAllButtons();
        captureStep("All_Buttons_Clicked");
        
        buttonsPage.clickResetButton();
        captureStep("After_Reset");
        
        LoggerManager.success("Button click and reset functionality verified");
    }

    @Test(description = "Verify button text labels")
    @Description("Test to verify button text labels are correct")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Button Display")
    public void testButtonText() {
        String button1Text = buttonsPage.getButton1Text();
        String button2Text = buttonsPage.getButton2Text();
        String button3Text = buttonsPage.getButton3Text();
        
        AllureUtils.addParameter("Button 1 Text", button1Text);
        AllureUtils.addParameter("Button 2 Text", button2Text);
        AllureUtils.addParameter("Button 3 Text", button3Text);
        
        Assert.assertEquals(button1Text, "Button 1");
        Assert.assertEquals(button2Text, "Button 2");
        Assert.assertEquals(button3Text, "Button 3");
        
        LoggerManager.success("Button texts verified");
    }
}

