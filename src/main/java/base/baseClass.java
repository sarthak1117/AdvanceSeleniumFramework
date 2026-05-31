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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


import org.apache.logging.log4j.Logger;
import actionDriver.ActionDriver;
import utils.ExtentManager;
import utils.LoggerManager;

public class baseClass {
 
   protected static Properties properties;
//   protected static WebDriver driver;
//   private static ActionDriver actionDriver;

   private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
   private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();


   public static final Logger logger = LoggerManager.getLogger(baseClass.class);

   @BeforeSuite
   public void loadconfig() throws FileNotFoundException, IOException {
               
      //Load the configuration file 
      properties = new Properties();
      FileInputStream  fis = new FileInputStream("src/main/resources/config.properties");

      // Load the File
      properties.load(fis);
      logger.info("Configuration file loaded successfully.");
      
      //Start the Extent Report before the test suite execution starts
      ExtentManager.getReporter();

   }
    
    @BeforeMethod
    @Parameters("browserName")
   public synchronized void setup(@Optional("chrome") String browserName) throws FileNotFoundException, IOException {

            loadconfig();
            launchBrowser(browserName);
            configureBrowser();
            logger.info("webdriver initialized and browser configured successfully.");
            logger.trace("Trace: WebDriver setup completed in setup() method.");
           // logger.error("Error: This is a test error log to verify logging configuration.");
            logger.debug("Debug: WebDriver instance created: " + driver);

            // Initialize ActionDriver with the WebDriver instance
            // if(actionDriver == null) {
            //     actionDriver = new ActionDriver(driver);
            //     logger.info("ActionDriver instance is created." + Thread.currentThread().threadId());
            // }

            // Initialize ActionDriver for the current thread
            actionDriver.set(new ActionDriver(getDriver()));
            logger.info("ActionDriver instance is created for thread: " + Thread.currentThread().threadId());
            logger.info("Browser setup completed successfully.");
           
   }

   public void launchBrowser(String browserName) throws FileNotFoundException, IOException {
           // Initialize WebDriver here based on deefined in .properties files 
           // String browser =properties.getProperty("browser");
            String browser = (browserName != null) ? browserName : properties.getProperty("browser");
            if(browser.equalsIgnoreCase("chrome")){
                  ChromeOptions options = new ChromeOptions();
                  options.addArguments("--disable-extensions");
                  options.addArguments("--no-first-run");
                  options.addArguments("--no-default-browser-check");
                  options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                  options.addArguments("--remote-allow-origins=*");
                  //driver = new ChromeDriver(options);
                  driver.set(new ChromeDriver(options));
                  ExtentManager.registerDriver(getDriver()); // Register the WebDriver instance with ExtentManager
                  logger.info("Chrome browser launched successfully.");
            }
            else if(browser.equalsIgnoreCase("firefox")){
                  //driver = new FirefoxDriver();
                  driver.set(new FirefoxDriver());
                  ExtentManager.registerDriver(getDriver()); // Register the WebDriver instance with ExtentManager
                  logger.info("Firefox browser launched successfully.");
            }
            else if(browser.equalsIgnoreCase("edge")){
                 //driver = new EdgeDriver();
                  driver.set(new EdgeDriver());
                  ExtentManager.registerDriver(getDriver()); // Register the WebDriver instance with ExtentManager
                 logger.info("Edge browser launched successfully.");
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
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

            //maximize the browser window
            getDriver().manage().window().maximize();

            //Navigate to the URL defined in the properties file
            getDriver().get(properties.getProperty("url"));
          
   }




  
   public void highlightElement(By locator) {
         WebElement element = getDriver().findElement(locator);
         JavascriptExecutor js = (JavascriptExecutor) getDriver();
         js.executeScript("arguments[0].style.border='3px solid yellow';", element);
   }

   public static Properties getProperties(){
      return properties;
   }

      @AfterMethod
      public void tearDown() {
                  if (getDriver() != null) {
                        getDriver().quit();
                  }
                  logger.info("Browser closed successfully.");
                  // driver = null; // Ensure driver is set to null after quitting
                  // actionDriver = null; // Reset ActionDriver instance for next test

                  driver.remove(); // Remove the WebDriver instance from ThreadLocal
                  actionDriver.remove(); // Remove the ActionDriver instance from ThreadLocal
      }

      //Getter method for driver
      public static WebDriver getDriver() {
            if (driver.get() == null) {
                  logger.error("WebDriver instance is null. It should have been initialized in setup().");
                  throw new IllegalStateException("WebDriver instance is not initialized. Ensure setup() has been called.");
            }
            return driver.get();
      }

      //Getter method for ActionDriver
      public static ActionDriver getActionDriver() {
            if (actionDriver.get() == null) {
                  logger.error("ActionDriver instance is null. It should have been initialized in setup().");
                  throw new IllegalStateException("ActionDriver instance is not initialized. Ensure setup() has been called.");
            }
            return actionDriver.get();
      }
}

