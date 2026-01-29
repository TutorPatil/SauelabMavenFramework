package com.saucelab.tests;

import com.saucelab.base.BaseClass;
import com.saucelab.utils.CommonUtils;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.annotations.Test;

public class LoginTests extends BaseClass implements ITestListener {


    @Test(groups = { "regression", "login","login_001" } )//,dataProvider = "logindata" , dataProviderClass = com.saucelab.dataproviders.DataProviders.class)
    //public static void login_001(String userNameValue, String passwordValue) throws Exception {
    public static void login_001() throws Exception {

    boolean result = CommonUtils.loginToSaucelabApp();

    //Assert.assertTrue(result);
    Assert.assertTrue(result,"Could not login to saucelab app!!");

    writeResultsToFile("Login_001","Pass");
    }

    @Test(groups = { "smoke", "login","login_002" } ) //,retryAnalyzer = BaseClass.class)
    public static void login_002() throws Exception {

        boolean result = CommonUtils.loginToSaucelabApp();
        Assert.assertFalse(result,"Could not login to saucelab app!!");
    }

    @Test(groups = { "smoke", "login","login_003" })
    public static void login_003() throws Exception {

        String locator = getLocatorDataFromExcel("Login","UserNameTextBox");
        System.out.println(locator);

        boolean result = CommonUtils.loginToSaucelabApp();
        Assert.assertTrue(result,"Could not login to saucelab app!!");
        // To test login

    }




}
