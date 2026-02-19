package Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;


public class BaseTest {

    public WebDriver driver;
    protected  WebDriverWait wait ;
    protected String browserName;


    //    @Parameters("browser")
    @BeforeSuite
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();


       // wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @AfterSuite
    public void tearDown(){

        driver.quit();
    }
}
