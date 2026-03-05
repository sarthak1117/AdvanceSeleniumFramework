package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actionDriver.ActionDriver;


public class HomePage {

    private ActionDriver actionDriver;

    //private String dynamicID = "//a[contains(text(),'Dynamic ID')]";
    //private String dynamicID = "//a[contains(.,'Dynamic ID')]"Dot operator is more robust then text();
    //private String dynamicID = "//a[@href='/classattr']";
    //private String classAttribute = "//a[contains(.,'Class Attribute')]"";

    // these locators currently unused; kept for future page interactions
    private By dynamicID = By.xpath("//a[contains(.,'Dynamic ID')]");
    private By classAttribute = By.xpath("//a[contains(.,'Class Attribute')]");
    private By hiddenLayers = By.xpath("//a[contains(.,'Hidden Layers')]");
    private By LoadDelay = By.xpath("//a[contains(.,'Load Delay')]");
    private By AJAXData = By.xpath("//a[contains(.,'AJAX Data')]");
    private By clientSideDelay = By.xpath("//a[contains(.,'Client Side Delay')]");
    private By click = By.xpath("//a[contains(.,'Click')]");
    private By textInput = By.xpath("//a[contains(.,'Text Input')]");
    private By scrollBars = By.xpath("//a[contains(.,'Scrollbars')]");
    private By dynamicTable = By.xpath("//a[contains(.,'Dynamic Table')]");
    private By verifyText = By.xpath("//a[contains(.,'Verify Text')]");
    private By progressBar = By.xpath("//a[contains(.,'Progress Bar')]");
    private By Visibility = By.xpath("//a[contains(.,'Visibility')]");
    private By sampleApp = By.xpath("//a[contains(.,'Sample App')]");
    private By mouseOver = By.xpath("//a[contains(.,'Mouse Over')]");
    private By nonBreakingSpace = By.xpath("//a[contains(.,'Non-Breaking Space')]");
    private By overlappedElement = By.xpath("//a[contains(.,'Overlapped Element')]");
    private By shadowDom = By.xpath("//a[contains(.,'Shadow DOM')]");
    private By alerts = By.xpath("//a[contains(.,'Alerts')]");
    private By fileUpload = By.xpath("//a[contains(.,'File Upload')]");
    private By animatedButton = By.xpath("//a[contains(.,'Animated Button')]");
    private By disableInput = By.xpath("//a[contains(.,'Disabled Input')]");
    private By autoWait = By.xpath("//a[contains(.,'Auto Wait')]");
    private By frames = By.xpath("//a[contains(.,'Frames')]");
    private By dynamicIDButtonXpath = By.xpath("//button[contains(@class,'btn-primary') and text()='Button with Dynamic ID']");
    //private By classAttribute = By.xpath("//button[contains(@class,'class1') and contains(@class,'btn-primary')]");
    private By classAttributeButtonXPath = By.xpath("//button[contains(concat(' ', normalize-space(@class), ' '), ' btn-primary ')]");// much more suitable then other one because handling the 
    // space on left right of  " btn-primary " is important. if i use above approach it will look for btn-primary which could also select btn-primary-outline.


    public HomePage(WebDriver driver){
        this.actionDriver = new ActionDriver(driver);
    }

    //Method to Perform action on Dynamic
    public void DynamicID() throws InterruptedException{
        System.out.println("Attempting to click the Dynamic ID button...");
        actionDriver.highlightElement(dynamicID);
        actionDriver.click(dynamicID);
        actionDriver.highlightElement(dynamicIDButtonXpath);
        Thread.sleep(5000); // Adding a short wait to ensure the element is interactable
        actionDriver.click(dynamicIDButtonXpath);
        
    }

    public void classAttribute(){
        System.out.println("Attempting to click the Class Attribute button...");
        actionDriver.click(classAttribute);
        actionDriver.highlightElement(classAttributeButtonXPath);
        try {
            Thread.sleep(2000); // Adding a short wait to ensure the element is interactable
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actionDriver.click(classAttributeButtonXPath);
        System.out.println("Alert text: " + actionDriver.getAlertText());
        actionDriver.acceptAlert();

    }    

}