package com.saucelab.tests;

import com.saucelab.base.BaseClass;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.File;

public class FileUploadTests extends BaseClass {

    @Test(groups = { "smoke", "fileupload","uploadFileExample_001" })
    public static void uploadFileExample_001() throws Exception {

        //Navigating to File upload application
        //https://www.tutorialspoint.com/selenium/practice/upload-download.php

        driver.get("https://www.tutorialspoint.com/selenium/practice/upload-download.php");

        File f = new File("./src/tools/selenium.png");
        String filePath = f.getAbsolutePath();

        // Uploading the file using the absolute path of the file..
        driver.findElement(By.xpath(getLocatorDataFomJason("FileUpload","ChooseFileButton"))).sendKeys(filePath);

        Thread.sleep(3000);
    }



}
