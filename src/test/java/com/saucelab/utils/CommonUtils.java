package com.saucelab.utils;

import com.saucelab.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CommonUtils extends BaseClass {

    public static boolean loginToSaucelabApp() throws Exception {

        test.info("Trying to launch and login to the sauce Lab application");
        boolean result ;
        String userName = getTestData("username");

        driver.findElement(By.xpath(getLocatorDataFomJason("LoginPage","usernameTextbox"))).sendKeys(userName);
        driver.findElement(By.xpath(getLocatorDataFromExcel("LoginPage","passwordTextbox"))).sendKeys(getTestData("password"));
        driver.findElement(By.xpath(getLocatorDataFomJason("LoginPage","loginButton"))).click();
        Thread.sleep(4000);
       // driver.switchTo().alert().dismiss();

        result = driver.findElement(By.xpath(getLocatorDataFromExcel("CartPage","cartIcon"))).isDisplayed();
        test.info("successfully logged in to  sauce Lab application");
        return result;
    }

    public static boolean loginToSaucelabApp(String userName,String password) throws Exception {

        boolean result ;

        driver.findElement(By.xpath(getLocatorDataFomJason("LoginPage","usernameTextbox"))).sendKeys(userName);
        driver.findElement(By.xpath(getLocatorDataFomJason("LoginPage","passwordTextbox"))).sendKeys(password);
        driver.findElement(By.xpath(getLocatorDataFromExcel("LoginPage","loginButton"))).click();
        Thread.sleep(4000);
        // driver.switchTo().alert().dismiss();

        result = driver.findElement(By.xpath(getLocatorDataFomJason("CartPage","cartIcon"))).isDisplayed();
        return result;
    }

    public static void clickElement(WebElement element)
    {
        element.click();
    }
}
