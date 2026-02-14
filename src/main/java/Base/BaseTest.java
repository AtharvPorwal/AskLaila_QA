package base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;


public class BaseTest {

    protected  WebDriver driver;
    protected  WebDriverWait wait ;
    protected String browserName;


    //    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser){
        browserName = browser;

        if(browser.equalsIgnoreCase("Chrome")){
            driver = new ChromeDriver();
        }

        //driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.asklaila.com/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    /*@AfterMethod
    public void tearDown(){

        driver.quit();
    } */
}
