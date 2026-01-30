package com.saucelab.tests;

import com.saucelab.base.BaseClass;
import com.saucelab.utils.CommonUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseClass {

    @Test(groups = { "smoke", "cart" ,"addItemToCart_001"})
    public static void addItemToCart_001()  throws Exception{
        boolean result = false;

        // Change the getLocatorDato use Json file data
        CommonUtils.loginToSaucelabApp();
        writeLogsToFile(" checking for SCM poll");
           writeLogsToFile(" checking for SCM poll 2");
        writeLogsToFile(" Logged to application, now tying to add item to cart..");
        driver.findElement(By.xpath(getLocatorData("backpackAddButton"))).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(getLocatorData("cartIcon"))).click();
        Thread.sleep(2000);
        Thread.sleep(2000);

        result = driver.findElement(By.xpath(getLocatorData("cartItemAdded"))).isDisplayed();
        Assert.assertTrue(result , "could not add the item to the cart!!");

    }
        }






