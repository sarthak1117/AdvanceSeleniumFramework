package actionDriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.baseClass;
import org.apache.logging.log4j.Logger;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;
    public static final Logger logger = baseClass.logger;

    //constructor to initialize the WebDriver and WebDriverWait
    public ActionDriver(WebDriver driver){
        this.driver = driver;
        int ExplicitWait  = Integer.parseInt(baseClass.getProperties().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ExplicitWait));
        logger.info("WebDriver instance is created");

    }
    //method to click on the element
    public void click(By by){
        String elementDescription = getElementDescription(by);
        try{
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
        logger.info("Clicked on element: " + elementDescription);
        }
        catch(Exception e){
            logger.error("Unable to click on the element:" + e.getMessage());
        }
    }
    //method to enter text in the element
    public void enterText(By by, String text){
        try{
            waitForElementToBeVisible(by);
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(text);
            logger.info("Entered text: " + text + " into element: " + getElementDescription(by));
        }
        catch(Exception e){
            logger.error("Unable to enter text in the element:" + e.getMessage());
        }        
    }

    //get text from the element
    public String getText(By by){
        try{
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        }
        catch(Exception e){
            logger.error("Unable to get text from the element:" + e.getMessage());
            return null;
        }
        
    }
    
    //compare the expected text with the actual text
    public void compareText(By by, String ExpectedText){
        try {
            waitForElementToBeVisible(by);
        String actualText = getText(by);
        if(actualText !=null && actualText.equals(ExpectedText)){
            logger.info("Text is matching: " + actualText);
        }
        else{
            logger.warn("Text is not matching. Expected: " + ExpectedText + ", Actual: " + actualText);
        }
            
        } catch (Exception e) {
            logger.error("Error comparing text: " + e.getMessage());
        }
        
    }

    public boolean isDisplayed(By by){
        try {
            waitForElementToBeVisible(by);
            logger.info("Element is displayed: " + getElementDescription(by));
            boolean isDisplayed = driver.findElement(by).isDisplayed();
            if(isDisplayed){
                return isDisplayed;
            }
        else return isDisplayed;
            
        } catch (Exception e) {
            logger.error("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageToLoad(int timeoutInSeconds){ 
        try { 
            wait.withTimeout(Duration.ofSeconds(timeoutInSeconds));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")); 
        } 
        catch (Exception e) { 
            logger.error("Error waiting for page to load: " + e.getMessage()); 
        } 
    }   

    public void scrollToElement(By by){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            waitForElementToBeVisible(by);
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
            
        } catch (Exception e) {
            logger.error("Error scrolling to element: " + e.getMessage());
        }        
    }

    //wait for the element to be clickable 
    public void waitForElementToBeClickable(By by){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch(Exception e){
            logger.error("Element not clickable: " + e.getMessage());
        }
    }

    //wait for the element to be visible
    public void waitForElementToBeVisible(By by){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        catch(Exception e){
            logger.error("Element not visible: " + e.getMessage());
        }

    }

    /* ---------- alert helpers ---------- */
    /**
     * Accepts the currently displayed alert if present.
     */
    public void acceptAlert(){
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        }
        catch(Exception e){
            logger.error("No alert to accept: " + e.getMessage());
        }
    }

    /**
     * Dismisses the currently displayed alert if present.
     */
    public void dismissAlert(){
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
        }
        catch(Exception e){
            logger.error("No alert to dismiss: " + e.getMessage());
        }
    }

    /**
     * Retrieves the text of the currently displayed alert or null if none.
     */
    public String getAlertText(){
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            return driver.switchTo().alert().getText();
        }
        catch(Exception e){
            logger.error("No alert present: " + e.getMessage());
            return null;
        }
    }

    /**
     * Sends text to a prompt alert and accepts it.
     */
    public void sendAlertText(String text){
        try{
            wait.until(ExpectedConditions.alertIsPresent());    
            driver.switchTo().alert().sendKeys(text);
            driver.switchTo().alert().accept();
        }
        catch(Exception e){
            logger.error("Unable to send text to alert: " + e.getMessage());
        }
    }

    //method to highlight element
    public void highlightElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red';", element);
        } catch (Exception e) {
            logger.error("Unable to highlight element: " + e.getMessage());
        }
    }

    public String getElementDescription(By locator) {
        //check for null driver or locator to avoid null pointer exception
        if(driver== null){
            return "driver is null";
        }
        if(locator== null){
            return "locator is null";
        }

        try{
            //find element using the locator
        WebElement element = driver.findElement(locator);

        //get Element Attirvbutes
        String name = element.getAttribute("name");
        String id = element.getAttribute("id");
        String className = element.getAttribute("class");
        String text = element.getText();
        String placeholder = element.getAttribute("placeholder");
        String type = element.getAttribute("type");
        String Value = element.getAttribute("value");
        String tagName = element.getTagName();

        //return a description based on available attributes, prioritizing the most unique ones
        if(!isNotEmpty(name)){
            return "Element with name:" +name;
        }
        if(!isNotEmpty(id)){
            return "Element with id:" +id;
        }
        if(!isNotEmpty(className)){
            return "Element with class:" +className;
        }
        if(!isNotEmpty(text)){
            return "Element with text:" +truncate(text,30);
        }
        if(!isNotEmpty(placeholder)){
            return "Element with placeholder:" +placeholder;
        }

        }
        catch(Exception e){
            logger.error("Unable to get element description: " + e.getMessage());
            return "Error getting element description: " + e.getMessage();
        }

        return null;
    }

//Utility method to check if a string is not empty or null
    public boolean isNotEmpty(String value){
        return value != null && !value.trim().isEmpty();
    }

//Utility method to truncate long text for better logging
    public String truncate(String value, int maxlength){
        if(value == null || value.length() <= maxlength){
            return value;
        }
        return value.substring(0, maxlength) + "...";
    }

}


