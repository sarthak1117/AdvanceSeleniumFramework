package tests;

import base.baseClass;
import pages.HomePage;
import utils.ExtentManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITestClass extends baseClass{
 
    private HomePage homePage;

    
    @BeforeMethod
    public void setUpPages() {
        System.out.println(">>> setUpPages() called - Creating HomePage instance");
        homePage = new HomePage(getDriver()); // Pass the WebDriver instance to the HomePage constructor
        System.out.println("HomePage instance created successfully: " + homePage);
    }
    @Test( enabled = true)
    public void testDynamicID() throws InterruptedException {
        ExtentManager.startTest("Valid Test");
        System.out.println(">>> testDynamicID() called - Thread: " + Thread.currentThread().getId());
        ExtentManager.logStepWithScreenshot(getDriver(), "Clicking on Dynamic ID link", "Dynamic ID Link Clicked");
         homePage.DynamicID();
        ExtentManager.logStepWithScreenshot(getDriver(), "Dynamic ID test completed successfully.", "Test Complete succcessfully");
        ExtentManager.endTest(); 
    }
    @Test(enabled = true)
    public void testClassAttribute() {
        ExtentManager.startTest("Class Attribute Test");
        System.out.println(">>> testClassAttribute() called - Thread: " + Thread.currentThread().getId());
        ExtentManager.logStepWithScreenshot(getDriver(), "Clicking on Class Attribute link", "Class Attribute Link Clicked");
        homePage.classAttribute();
        ExtentManager.logStepWithScreenshot(getDriver(), "Class Attribute test completed successfully.", "Class Attribute Test Complete");
        ExtentManager.endTest();
    }
    
    // @Test   
    // public void testHiddenLayers() {
    //     homePage.hiddenLayers();
    // }
}
