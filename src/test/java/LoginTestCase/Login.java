package LoginTestCase;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class Login_Credentials {

    Object[][]data=null;
    WebDriver driver;


    @DataProvider(name = "loginCredentials")
    public Object[][] loginDataProvider() throws BiffException, IOException {

        data=getExeldata();
        return data;

    }

    public Object[][] getExeldata() throws IOException, BiffException {
        FileInputStream fileInputStream=new FileInputStream("F:\\Notes\\Credentials.xls");
        Workbook workbook=Workbook.getWorkbook(fileInputStream);
        Sheet sheet=workbook.getSheet(0);

        int rcount=sheet.getRows();
        int ccount=sheet.getColumns();

        Object testData[][]=new Object[rcount-1][ccount];

        for (int i=1;i<rcount;i++){
            for ( int j=0;j<ccount;j++){
                testData[i-1][j]= sheet.getCell(j,i).getContents();
            }

        }
        return testData;
    }
    @BeforeTest
    public void openBrowser(){
        driver =new ChromeDriver();
    }
    @AfterTest
    public void closebrowser(){
        driver.quit();
    }




    @Test(dataProvider ="loginCredentials")
    public void loginwithBIC(String Uname,String Pword){


        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement username= driver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys(Uname);

        WebElement password=driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys(Pword);

        WebElement login= driver.findElement(By.xpath("//button[@type='submit']"));
        login.click();

    }



}
