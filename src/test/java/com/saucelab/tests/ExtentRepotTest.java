package com.saucelab.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentRepotTest {

    public static ExtentSparkReporter spark;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void main(String[] args) {
        // Create the Object of Report
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

        // Adding the test case data to the report
        test = extent.createTest("Starting the test case execution");
        test.info("*************Test case started now starting validation****************");
        test.info("Launched the browser now continuing login..");
        test.info("Successfully logged in");
        //test.pass("Login_001 Passed");
        test.fail("Login_001 Test failed");

        // Attach he screenshot of failed test case
        File f = new File("./src/test/java/results/screenshots/login_002.png");
        String filePath =f.getAbsolutePath();
        test.addScreenCaptureFromPath(filePath,"Login_002 Failed");
        extent.flush();
















    }

}
