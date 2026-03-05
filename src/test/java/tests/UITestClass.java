package tests;

import base.baseClass;
import pages.HomePage;
import org.testng.annotations.Test;

public class UITestClass extends baseClass{
 
    private HomePage homePage;

    public void setUpPages() {
        homePage = new HomePage(driver);
    }
    @Test
    public void testDynamicID() throws InterruptedException {
        setUpPages();
         homePage.DynamicID();
    }
    @Test
    public void testClassAttribute() {
        setUpPages();
        homePage.classAttribute();
    }
}
