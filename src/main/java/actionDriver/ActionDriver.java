package actionDriver;

import java.time.Duration;

import org.openqa.selenium.By;
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

    public void click(By by){
        try{
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
        }
        catch(Exception e){
            System.out.println("Unable to click on the element:" + e.getMessage());
        }
    }

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


