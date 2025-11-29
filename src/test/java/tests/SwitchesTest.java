package tests;

import base.BaseTest;
import core.LoggerManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Switches Tab functionality tests
 */
public class SwitchesTest extends BaseTest {

    @BeforeMethod
    public void setupSwitchesTab() {
        performLogin();
        switchesPage = listPage.navigateToSwitchesTab();
    }

    @Test(description = "Verify Switches tab is displayed")
    public void testSwitchesTabDisplayed() {
        captureStep("SwitchesTab_Initial");

        Assert.assertTrue(switchesPage.isPageLoaded(),
                "Switches tab should be displayed");
        Assert.assertTrue(switchesPage.isSwitch1Displayed(),
                "Switch 1 should be displayed");
        Assert.assertTrue(switchesPage.isSwitch2Displayed(),
                "Switch 2 should be displayed");
        Assert.assertTrue(switchesPage.isSwitch3Displayed(),
                "Switch 3 should be displayed");
        Assert.assertTrue(switchesPage.isSaveButtonDisplayed(),
                "Save button should be displayed");

        LoggerManager.success("All switches displayed");
    }

    @Test(description = "Verify Switch 1 toggle")
    public void testSwitch1Toggle() {
        boolean initialState = switchesPage.isSwitch1On();
        LoggerManager.info("Switch 1 initial state: " + (initialState ? "ON" : "OFF"));

        switchesPage.toggleSwitch1();
        captureStep("Switch1_Toggled");

        boolean newState = switchesPage.isSwitch1On();
        LoggerManager.info("Switch 1 new state: " + (newState ? "ON" : "OFF"));

        Assert.assertNotEquals(initialState, newState,
                "Switch state should change after toggle");

        LoggerManager.success("Switch 1 toggled successfully");
    }

    @Test(description = "Verify Switch 2 toggle")
    public void testSwitch2Toggle() {
        boolean initialState = switchesPage.isSwitch2On();

        switchesPage.toggleSwitch2();
        captureStep("Switch2_Toggled");

        boolean newState = switchesPage.isSwitch2On();
        Assert.assertNotEquals(initialState, newState);

        LoggerManager.success("Switch 2 toggled successfully");
    }

    @Test(description = "Verify Switch 3 toggle")
    public void testSwitch3Toggle() {
        boolean initialState = switchesPage.isSwitch3On();

        switchesPage.toggleSwitch3();
        captureStep("Switch3_Toggled");

        boolean newState = switchesPage.isSwitch3On();
        Assert.assertNotEquals(initialState, newState);

        LoggerManager.success("Switch 3 toggled successfully");
    }

    @Test(description = "Verify Save button functionality")
    public void testSaveButtonSavesStates() {
        // Turn all switches ON
        switchesPage.turnOnAllSwitches();
        captureStep("All_Switches_ON");

        // Click Save
        switchesPage.clickSaveButton();
        captureStep("After_Save");

        // Verify state text
        String stateText = switchesPage.getSaveStateText();
        LoggerManager.info("Save state text: " + stateText);

        Assert.assertTrue(stateText.contains("ON"),
                "State text should contain ON after saving");

        LoggerManager.success("Switch states saved");
    }

    @Test(description = "Verify turning all switches ON")
    public void testTurnAllSwitchesOn() {
        switchesPage.turnOnAllSwitches();
        captureStep("All_ON");

        Assert.assertTrue(switchesPage.isSwitch1On(), "Switch 1 should be ON");
        Assert.assertTrue(switchesPage.isSwitch2On(), "Switch 2 should be ON");
        Assert.assertTrue(switchesPage.isSwitch3On(), "Switch 3 should be ON");

        LoggerManager.success("All switches turned ON");
    }

    @Test(description = "Verify turning all switches OFF")
    public void testTurnAllSwitchesOff() {
        // First turn ON
        switchesPage.turnOnAllSwitches();

        // Then turn OFF
        switchesPage.turnOffAllSwitches();
        captureStep("All_OFF");

        Assert.assertFalse(switchesPage.isSwitch1On(), "Switch 1 should be OFF");
        Assert.assertFalse(switchesPage.isSwitch2On(), "Switch 2 should be OFF");
        Assert.assertFalse(switchesPage.isSwitch3On(), "Switch 3 should be OFF");

        LoggerManager.success("All switches turned OFF");
    }
}
