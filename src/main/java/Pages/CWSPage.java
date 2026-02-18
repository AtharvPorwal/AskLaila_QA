package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CWSPage {
    WebDriver driver;
    WebDriverWait wait;

    By selectCategory = By.xpath("//span[text()='Category']");
    By category_option = By.xpath("//a[text()='Car Service Centre']");


    By selectLocalities = By.xpath("//span[text()='Localities']");
    By localities_option = By.xpath("//a[@title='Top Car Washing Services in T.nagar']");


    By selectSub = By.xpath("//span[text()='Sub-Category']");
    By sub_option = By.xpath("//a[@title='Car Washing Services in t nagar, Chennai']");

    public CWSPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // SIMPLE WAIT
    }

    public void clickCategory(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectCategory));
        driver.findElement(selectCategory).click();
    }
    public void clickCategoryOptions(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(category_option));
        driver.findElement(category_option).click();
    }

    public void clickLocalities(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectLocalities));
        driver.findElement(selectLocalities).click();
    }

    public void clickLocalitiesOptions(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(localities_option));
        driver.findElement(localities_option).click();
    }

    public void clickSub(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectSub));
        driver.findElement(selectSub).click();
    }

    public void clickSubOptions(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(sub_option));
        driver.findElement(sub_option).click();
    }



}
