package com.saucelab.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;
import org.testng.annotations.*;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class BaseClass implements ITestListener,IRetryAnalyzer {

    public static WebDriver driver = null;
    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    public static ExtentSparkReporter spark;
    public static ExtentReports extent;
    public static ExtentTest test;

    static Logger logger =  LogManager.getLogger(BaseClass.class);


    public static void writeLogsToFile(String msg)
    {
        logger.info(msg);
        test.info(msg);
    }

    public void writeErrorLogs(String msg)
    {

        logger.error(msg);
    }

    /**
     * @throws IOException
     * @Author Patil
     * @Description This method is to launch the application using the required browser
     */

    @BeforeMethod(alwaysRun = true)
    public static void launchApplication() throws IOException {

        //String browser = getConfigData("browser");
       String browser = System.getProperty("browser");
        String env = System.getProperty("env");
        System.out.println("*******" +env);
//        String baseUrl = System.getProperty("url");
//        String browser = System.getProperty("execution.browser");
 //       String env = System.getProperty("execution.environment");




        if (browser.equalsIgnoreCase("chrome")) {
            System.out.println("running the tests using chrome browser");

            ChromeOptions options = new ChromeOptions();

            // Disable password manager prefs
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

            options.setExperimentalOption("prefs", prefs);

            // launching chrome browser with password pop up disabled....
            driver = new ChromeDriver(options);

        }

        if (browser.equalsIgnoreCase("chromeheadless")) {
            System.out.println("running the tests using chromeheadless browser");

            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--headless=new",
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
            driver = new ChromeDriver(options);

        }

        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        if (browser.equalsIgnoreCase("edge")) {
            System.out.println("running the tests using edge browser");
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.get(getConfigData("url"));
        // Adding implicit wait
        int waittime = Integer.parseInt(getConfigData("waittime"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waittime));

    }


    public static String getConfigData(String key) throws IOException {
        String propVal = "";

        File f = new File("./src/test/java/data/config.properties");
        FileInputStream fio = new FileInputStream(f);

        // Creating the object of properties file
        Properties prop = new Properties();

        // using the load method of properties class to load the input stream
        prop.load(fio);

        // to get  the property value based on key
        propVal = prop.getProperty(key);
        return propVal;

    }

    public static String getLocatorData(String key) throws IOException {
        String propVal = "";

        File f = new File("./src/test/java/data/locatordata.properties");
        FileInputStream fio = new FileInputStream(f);

        // Creating the object of properties file
        Properties prop = new Properties();

        // using the load method of properties class to load the input stream
        prop.load(fio);

        // to get  the property value based on key
        propVal = prop.getProperty(key);
        return propVal;

    }

    public static String getLocatorDataFomJason(String pageName, String elementName) throws FileNotFoundException {
        String locator = "";
        Reader reader = new FileReader(new File("./src/test/java/data/locatordatafile.json"));
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        locator = jsonObject.get(pageName).getAsJsonObject().get(elementName).getAsString();
        return locator;

    }


    public static String getTestData(String key) throws IOException {
        String propVal = "";

        File f = new File("./src/test/java/data/testdata.properties");
        FileInputStream fio = new FileInputStream(f);

        // Creating the object of properties file
        Properties prop = new Properties();

        // using the load method of properties class to load the input stream
        prop.load(fio);

        // to get  the property value based on key
        propVal = prop.getProperty(key);
        return propVal;

    }

    @AfterMethod(alwaysRun = true)
    public static  void closeBrowser()
    {
        driver.quit();
    }


    public static void writeResultsToFile(String testCaseName, String testCaseStatus) throws IOException {

        //File f = new File("C:\\Users\\Sudhanva\\Desktop\\SaagTrainings\\SauceDemoAppAutomation\\src\\results\\results.txt");
        File f = new File("./src/test/java/results/results.txt");
        FileWriter fw = new FileWriter(f,true);

        fw.write(testCaseName +"-----"+testCaseStatus+"\n");

        fw.flush();
        fw.close();


    }
//
//    public static void writeLogsToFile(String logMsg) throws IOException {
//
//        //File f = new File("C:\\Users\\Sudhanva\\Desktop\\SaagTrainings\\SauceDemoAppAutomation\\src\\results\\logs.txt");
//        File f = new File("./src/test/java/results/logs.txt");
//        FileWriter fw = new FileWriter(f,true);
//
//        fw.write(logMsg +"\n");
//        System.out.println(logMsg);
//
//        fw.flush();
//        fw.close();
//
//
//    }

    public static String captureScreenShot(String fileName) throws IOException {


        // Down casting driver to Take Screenshot level and capturng screen shot as file
        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        // Creating a file class object to save the screen shot as a file
        File dest = new File("./src/test/java/results/screenshots/"+fileName+".png");

        // Copying src to destination ,, this will create the physical file in the destination location
        FileHandler.copy(source, dest);

       String filePath =  dest.getAbsolutePath();

        return  filePath;
    }

    @BeforeSuite(alwaysRun = true)
    public static void clearResultsAndLogs() throws IOException {




    }


    @AfterSuite(alwaysRun = true)
    public void afterSuiteMethod() throws IOException {

        writeLogsToFile(" This method will run after the complete execution");

    }

    @BeforeClass(alwaysRun = true)
    public static  void beforeClassMethod()
    {
        System.out.println("This method will run before every class");
    }

    @AfterClass(alwaysRun = true)
    public static  void afterClassMethod()
    {
        System.out.println("This method will run after every class");
    }


    @BeforeTest(alwaysRun = true)
    public static void beforeTestMethod()
    {
        System.out.println("This method will be run before testng.xml Test");
    }

    @AfterTest(alwaysRun = true)
    public static void atfertTestMethod()
    {
        System.out.println("This method will be run after testng.xml Test");
    }


    // ITest Lister methods below
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
        writeLogsToFile("Starting the test case "+ result.getName());

    }

    public  void onTestSuccess(ITestResult result) {
        try {
            //writeLogsToFile("The test case "+ result.getName()+ " is Pass");
            writeResultsToFile(result.getName(),"Pass");
            test.pass("The test case "+ result.getName()+ " is Pass");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTestFailure(ITestResult result) {
        try {
            //writeLogsToFile("The test case "+ result.getName()+ " is Failed!!");
            writeResultsToFile(result.getName(),"Fail");
            String filePath = captureScreenShot(result.getName());
            String execeptionMessage = result.getThrowable().toString();
            writeErrorLogs(execeptionMessage);
            test.addScreenCaptureFromPath(filePath,result.getName());
            test.fail(result.getThrowable());
            test.fail(result.getName());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void onTestSkipped(ITestResult result) {
        writeLogsToFile("The test case "+ result.getName()+ " is getting skipped");
    }

    public void onStart(ITestContext context) {
        try {

            spark =  new ExtentSparkReporter("./src/test/java/results/ExtentReport.html");

            // Add config
            spark.config().setReportName("Sauce Lab Automation Test Report");
            spark.config().setDocumentTitle("Selenium Execution");

            // Attach the report
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Adding some more meta data in the report
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", "Chrome");


            //writeLogsToFile("The execution is starting...");
            // Clearing the previous run logs from the logfile
            File f = new File("./src/test/java/results/logs.txt");
            FileWriter fw = new FileWriter(f);
            fw.write("Capturing the logs for the current run \n");
            fw.flush();
            fw.close();

            // Clearing the previous run results from the logfile
            File f1 = new File("./src/test/java/results/results.txt");
            FileWriter fw1 = new FileWriter(f1);
            fw1.write("Capturing the Results for the current run \n");
            fw1.flush();
            fw1.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onFinish(ITestContext context) {

        writeLogsToFile("The execution is ending...");
        extent.flush();
    }


    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }

    public static String getLocatorDataFromExcel(String pageName, String elementName) throws IOException {

        String locatorData = "";

        File f = new File("./src/test/java/data/locatordata.xlsx");
        FileInputStream fis = new FileInputStream(f);
        XSSFWorkbook wb =  new XSSFWorkbook(fis);
        XSSFSheet ws = wb.getSheetAt(0);

        int rows = ws.getLastRowNum();

        for(int i = 1; i <= rows; i++) {
            String page = ws.getRow(i).getCell(0).getStringCellValue();
            String element = ws.getRow(i).getCell(1).getStringCellValue();
            if( page.equalsIgnoreCase(pageName) && (element.equalsIgnoreCase(elementName))){
                locatorData = ws.getRow(i).getCell(2).getStringCellValue();
                break;
            }
        }
        return locatorData;

    }




    //String locator = readDataFromExcelFile("LoginPage","usernameTextbox");
    //System.out.println(locator);
    public static String readDataFromExcelFile(String pageName, String elementName) throws Exception
        {

            String locator = "";
            // Creating the object of file class and then taking the whole file as input stream to get all the contents of the file
           File f = new File("./src/test/java/data/locatordata.xlsx");
           FileInputStream fis = new FileInputStream(f);

           // Creating the object as per Excel object - Workbook
            XSSFWorkbook wb = new XSSFWorkbook(fis);

            // Creatig the object of work sheet
            XSSFSheet ws = wb.getSheetAt(0);

            int rows = ws.getLastRowNum();
            System.out.println(" The number of rows are "+rows);

            for ( int x=1;x<=rows;x++ ) {
                String page = ws.getRow(x).getCell(0).getStringCellValue();
                String element = ws.getRow(x).getCell(1).getStringCellValue();

                if( page.equalsIgnoreCase(pageName) && (element.equalsIgnoreCase(elementName))){
                    locator =  ws.getRow(x).getCell(2).getStringCellValue();
                 }
            }
            return locator;
        }
}
