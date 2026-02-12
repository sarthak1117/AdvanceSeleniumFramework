package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class baseClass {
 
   protected static Properties properties;

   protected WebDriver driver;

   @BeforeSuite
   public void loadconfig() throws FileNotFoundException, IOException {
               
         //Load the configuration file 
      properties = new Properties();
         FileInputStream  fis = new FileInputStream("src/main/resources/config.properties");

            // Load the File
            properties.load(fis);

   }

    @BeforeMethod
   public void setup() throws FileNotFoundException, IOException {

            loadconfig();
            launchBrowser();
            configureBrowser();
           
   }

   public void launchBrowser() throws FileNotFoundException, IOException {
           // Initialize WebDriver here based on deefined in .properties files 
            String browser =properties.getProperty("browser");
            if(browser.equalsIgnoreCase("chrome")){
                  driver = new ChromeDriver();
            }
            else if(browser.equalsIgnoreCase("firefox")){
                  driver = new FirefoxDriver();
            }
            else if(browser.equalsIgnoreCase("edge")){
                 driver = new EdgeDriver();
            }
            else{
                  System.out.println("Unsupported browser specified in properties file.");
            }

            if (driver == null) {
                  throw new IllegalStateException("Browser not initialized. Check 'browser' in config.properties: " + browser);
            }
   }

   public void configureBrowser() throws FileNotFoundException, IOException {

       //Define the implicit wait as it is global wait
            int implicitwait = Integer.parseInt(properties.getProperty("implicitWait"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

            //maximize the browser window
            driver.manage().window().maximize();

            //Navigate to the URL defined in the properties file
            driver.get(properties.getProperty("url"));
          
   }

  
   public void highlightElement(By locator) {
         WebElement element = driver.findElement(locator);
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("arguments[0].style.border='3px solid red';", element);
   }

      @AfterMethod
      public void tearDown() {
                  if (driver != null) {
                        driver.quit();
                  }
            }
}

