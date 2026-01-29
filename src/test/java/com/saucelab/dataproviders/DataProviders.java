package com.saucelab.dataproviders;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "logindata")
    public  Object[][] supplyLoginData() throws Exception {
        Object[][] x = new Object[3][2];
        File f = new File("./src/test/java/data/testdata.xlsx");
        FileInputStream fis = new FileInputStream(f);
        XSSFWorkbook wb =  new XSSFWorkbook(fis);
        XSSFSheet ws = wb.getSheetAt(0);

            for(int i=1;i<=3;i++) {
                for(int j=0;j<=1;j++) {
                    x[i-1][j] =ws.getRow(i).getCell(j).getStringCellValue();
                }
            }
        return  x;
    }

/*
    @DataProvider(name = "logindata")
    public  Object[][] supplyLoginData()
    {
        Object[][] x = new Object[3][2];

        x[0][0] = "standard_user";
        x[0][1] = "secret_sauce";


        x[1][0] = "standard_user";
        x[1][1] = "secret_sauce";


        x[2][0] = "visual_user";
        x[2][1] = "secret_sauce";


        return  x;
    }

*/
}
