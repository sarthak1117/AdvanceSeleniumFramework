package pages;



// import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import actionDriver.ActionDriver;
import base.baseClass;


public class HomePage {

    private ActionDriver actionDriver;
    public static final Logger logger = baseClass.logger;

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
    private By HiddenLayersButtonXPath = By.xpath("//button[@id='greenButton']");

    // Constructor to initialize ActionDriver by passing the WebDriver instance from the test class
    /* public HomePage(WebDriver driver){
        this.actionDriver = new ActionDriver(driver);
    } */

    //Instead of calling ActionDriver in every page class and creating its object which will create multiple instances, we can initialize it once in the baseClass and then use it across all page classes.
    // This way we ensure that we are using the same WebDriver instance and also avoid redundant code.
    // It is called Singleton pattern for ActionDriver.
    public HomePage(WebDriver driver){
        this.actionDriver = baseClass.getActionDriver();
    }
    
    //Method to Perform action on Dynamic
    public void DynamicID() throws InterruptedException{
        logger.info("Attempting to click the Dynamic ID button...");
        actionDriver.highlightElement(dynamicID);
        actionDriver.click(dynamicID);
        actionDriver.highlightElement(dynamicIDButtonXpath);
        Thread.sleep(5000); // Adding a short wait to ensure the element is interactable
        actionDriver.click(dynamicIDButtonXpath);
        
    } //  180030111111

    public void classAttribute(){
        logger.info("Attempting to click the Class Attribute button...");
        actionDriver.click(classAttribute);
        actionDriver.highlightElement(classAttributeButtonXPath);
        try {
            Thread.sleep(2000); // Adding a short wait to ensure the element is interactable
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for element to be interactable: " + e.getMessage());
        }
        actionDriver.click(classAttributeButtonXPath);
        logger.info("Alert text: " + actionDriver.getAlertText());
        actionDriver.acceptAlert();

    }    

    public void hiddenLayers(){
        // 1. Navigate to the page and highlight
    actionDriver.click(hiddenLayers);
    actionDriver.highlightElement(HiddenLayersButtonXPath);
    
    // 2. First click - This creates the "Hidden Layer" (Blue Button)
    actionDriver.click(HiddenLayersButtonXPath);
    System.out.println("First click successful.");

    // 3. The Test Logic
    try {
        // We attempt the second click on the SAME XPath
        actionDriver.click(HiddenLayersButtonXPath); 
        
        // If we reach this line, the test logic failed because the layer didn't block us
        Assert.fail("The button should not have been clickable a second time!");
        
    } catch (Exception e) { 
        // Use Exception e generally if actionDriver wraps the original Selenium exception
        if (e.getMessage().contains("click intercepted")) {
            System.out.println("Success: Caught expected interception error.");
            
            // Extracting the 'Who' that blocked us
            if (e.getMessage().contains("receive the click:")) {
                System.out.println("Intercepted by: " + e.getMessage().split("receive the click:")[1]);
            }
            
            // IMPORTANT: We do NOT re-throw or fail here. 
            // By ending the method here, the test is marked as PASSED.
        } else {
            // If it's a DIFFERENT error (like a timeout), we should still fail.
            Assert.fail("Test failed due to an unexpected error: " + e.getMessage());
        }
    }
        
    }

}