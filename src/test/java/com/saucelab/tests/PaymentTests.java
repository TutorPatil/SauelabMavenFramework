package com.saucelab.tests;

import com.saucelab.base.BaseClass;
import com.saucelab.utils.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaymentTests extends BaseClass {

    public static void test_001()
    {
        WebElement btn = driver.findElement(By.xpath("//input[@id='username'']"));
        CommonUtils.clickElement(btn);
    }
}
