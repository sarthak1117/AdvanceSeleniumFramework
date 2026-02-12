package tests;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import base.baseClass;


public class DummyClass extends baseClass{

    @Test
    public void dummyMethod() throws InterruptedException {
        // This is a dummy method for testing purposes.
        Thread.sleep(2000); // Simulate some test actions
        driver.switchTo().defaultContent();
       By elementLocator = By.xpath("//h1[contains(.,'UI Test Automation') and contains(.,'Playground')]");
       // driver.findElement(elementLocator);
       highlightElement(elementLocator);
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        assert title.equals("UI Test Automation Playground")   : "Page title does not match expected value.";
        System.out.println("Dummy method executed successfully. Page title: " + title); 
    }
        
}
