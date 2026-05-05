package tests;

import base.baseClass;
import pages.HomePage;

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
    @Test
    public void testDynamicID() throws InterruptedException {
         homePage.DynamicID();
    }
    @Test
    public void testClassAttribute() {
        homePage.classAttribute();
    }

    // @Test   
    // public void testHiddenLayers() {
    //     homePage.hiddenLayers();
    // }
}
