package tests;

import base.BaseTest;
import core.LoggerManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * List Tab functionality tests
 * Core tests for list feature
 */
@Epic("Mobile App Testing")
@Feature("List Feature")
public class ListTest extends BaseTest {

    @BeforeMethod
    public void loginBeforeTest() {
        performLogin();
    }

    @Test(description = "Verify List tab is displayed after login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("List Display")
    public void testListTabDisplayed() {
        captureStep("ListTab_Displayed");
        
        Assert.assertTrue(listPage.isPageLoaded(), "List tab should be displayed");
        
        String instructions = listPage.getInstructionsText();
        Assert.assertTrue(instructions.contains("Explore"), "Instructions should contain 'Explore'");
        
        LoggerManager.success("List tab displayed correctly");
    }

    @Test(description = "Verify list items are displayed")
    @Severity(SeverityLevel.NORMAL)
    @Story("List Display")
    public void testListItemsDisplayed() {
        int itemCount = listPage.getListItemCount();
        LoggerManager.info("Found " + itemCount + " list items");
        
        Assert.assertTrue(itemCount > 0, "Should have at least one list item");
        Assert.assertTrue(itemCount >= 10, "Should have at least 10 items visible");
        
        LoggerManager.success("List items displayed");
    }

    @Test(description = "Verify navigation to Buttons tab")
    @Severity(SeverityLevel.NORMAL)
    @Story("Navigation")
    public void testNavigationToButtonsTab() {
        buttonsPage = listPage.navigateToButtonsTab();
        
        Assert.assertTrue(buttonsPage.isPageLoaded(), "Should navigate to Buttons tab");
        captureStep("ButtonsTab_FromList");
        
        LoggerManager.success("Navigation to Buttons tab works");
    }
}

