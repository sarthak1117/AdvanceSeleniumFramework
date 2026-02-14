package actionDriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    //constructor to initialize the WebDriver and WebDriverWait
    public ActionDriver(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }
    //method to click on the element
    public void click(By by){
        try{
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
        }
        catch(Exception e){
            System.out.println("Unable to click on the element:" + e.getMessage());
        }
    }
    //method to enter text in the element
    public void enterText(By by, String text){
        try{
            waitForElementToBeVisible(by);
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(text);
        }
        catch(Exception e){
            System.out.println("Unable to enter text in the element:" + e.getMessage());
        }        
    }

    //get text from the element
    public String getText(By by){
        try{
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        }
        catch(Exception e){
            System.out.println("Unable to get text from the element:" + e.getMessage());
            return null;
        }
        
    }
    
    //compare the expected text with the actual text
    public void compareText(By by, String ExpectedText){
        try {
            waitForElementToBeVisible(by);
        String actualText = getText(by);
        if(actualText !=null && actualText.equals(ExpectedText)){
            System.out.println("Text is matching: " + actualText);
        }
        else{
            System.out.println("Text is not matching. Expected: " + ExpectedText + ", Actual: " + actualText);
        }
            
        } catch (Exception e) {
            System.out.println("Error comparing text: " + e.getMessage());
        }
        
    }

    public boolean isDisplayed(By by){
        try {
            waitForElementToBeVisible(by);
            boolean isDisplayed = driver.findElement(by).isDisplayed();
            if(isDisplayed){
                System.out.println("Element is displayed: " + by.toString());
                return isDisplayed;
            }
        else return isDisplayed;
            
        } catch (Exception e) {
            System.out.println("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageToLoad(){ 
        try { 
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")); 
        } 
        catch (Exception e) { 
            System.out.println("Error waiting for page to load: " + e.getMessage()); 
        } 
    }   

    public void scrollToElement(By by){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            waitForElementToBeVisible(by);
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
            
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }        
    }

    //wait for the element to be clickable 
    public void waitForElementToBeClickable(By by){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch(Exception e){
            System.out.println("Element not clickable: " + e.getMessage());
        }
    }

    //wait for the element to be visible
    public void waitForElementToBeVisible(By by){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        catch(Exception e){
            System.out.println("Element not visible: " + e.getMessage());
        }

    }
}


