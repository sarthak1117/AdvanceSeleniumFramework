package utils;

import java.io.File;
import java.io.IOException; 
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;   


public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap<>();


    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            String ReportPath = System.getProperty("user.dir") + "/test-output/ExtentReports/ExtentReport.html";
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(ReportPath);
            spark.config().setDocumentTitle("UI Test Automation Playground - Extent Report");
            spark.config().setReportName("UI Test Automation Playground - Test Execution Report");
            spark.config().setTheme(Theme.DARK);

            extent.attachReporter(spark);
        }
        return extent;
    }

    //start test and assign to thread
    public synchronized static ExtentTest startTest(String testName) {
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    //End test and remove from thread
    public synchronized static void endTest() {
        getReporter().flush();
    }

    //Get the current test assigned to the thread
    public synchronized  static ExtentTest getTest() {
        return test.get();
    }

    //Method to get the name of the current test method being executed
    public synchronized static String getCurrentTestMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("tests.") && element.getMethodName().startsWith("test")) {
                return element.getMethodName();
            }
        }
        return "UnknownTestMethod";
    }

    //Log a step
    public static void logStep(String LogMessage) {
        getTest().info(LogMessage);
    }


    
    //Log a pass with screenshot
    public static void logStepWithScreenshot(WebDriver driver, String LogMessage, String ScreenShotMessage) {
        getTest().pass(LogMessage);
        attachScreenshot(driver, ScreenShotMessage);
    }

    //Log a failure with screenshot
    public static void logFailureWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage) {
        String colorMessage = "<span style='color:red; font-weight:bold;'>" + logMessage + "</span>";
        getTest().fail(colorMessage);
        attachScreenshot(driver, screenShotMessage);
    }

    //Log a skipped test
    public static void logSkip(String LogMessage) {
        String colorMessage = "<span style='color:orange; font-weight:bold;'>" + LogMessage + "</span>";
        getTest().skip(colorMessage);
    }

    //Take a screenshot with date-time stamp and return the path
    public synchronized static String takeScreenshot(WebDriver driver, String screenShotName) { 
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/test-output/Screenshots/" + screenShotName + "_" + timestamp + ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String base64Image = convertScreenshotToBase64(source);
        return base64Image;
        //return screenshotPath;
    }

    //convert screenshot to base64 string
    public static String convertScreenshotToBase64(File screenshotFile) {
        String base64Image = "";
        //Read the file content into a byte array
        byte[] fileContent ;
        try {
            fileContent = FileUtils.readFileToByteArray(screenshotFile);
            base64Image= Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;

    } 

    //attach screenshot to the report using base64 
    public synchronized static void attachScreenshot(WebDriver driver, String message) {
        try {
            String ScreenShotBase64 = takeScreenshot(driver, getCurrentTestMethodName());
            getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenShotBase64).build());
            
        } catch (Exception e) {
            // TODO: handle exception
            getTest().fail("Failed To attach the Screenshot:"+ message);
            e.printStackTrace();
        }
        
    }


    //Register driver instance for the current thread
    public static void registerDriver(WebDriver driver) {
        driverMap.put(Thread.currentThread().getId() , driver);
    }
}
