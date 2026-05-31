package tests;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Test;
import base.baseClass;
import utils.ExtentManager;


public class DummyClass extends baseClass{

    @Test
    public void dummyMethod() throws InterruptedException {
        // This is a dummy method for testing purposes.
        ExtentManager.startTest("Dummy Test");
        System.out.println(">>> dummyMethod() called - Thread: " + Thread.currentThread().getId());
        Thread.sleep(2000); // Simulate some test actions
        getDriver().switchTo().defaultContent();
       By elementLocator = By.xpath("//h1[contains(.,'UI Test Automation') and contains(.,'Playground')]");
       // driver.findElement(elementLocator);
       highlightElement(elementLocator);
        String title = getDriver().getTitle();
        System.out.println("Page Title: " + title);
        assert title.equals("UI Test Automation Playground")   : "Page title does not match expected value.";
        // ExtentManager.logStep("Dummy method executed successfully. Page title: " + title);
        ExtentManager.logSkip("Skipping the test as part of Testing");
        // ExtentManager.getTest().skip("Skipping the test as part of Testing");
        throw new SkipException("Skipping the test as part of Testing"); 
    }
        
}
