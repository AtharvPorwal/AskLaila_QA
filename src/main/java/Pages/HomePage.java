package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    By IndiaCard = By.xpath("//img[contains(@class,'img-responsive')]");

    By ChennaiCard = By.xpath("//img[contains(@src,'tile-Chennai.png')]");

    By searchInput = By.xpath("//input[@placeholder='Search for businesses']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // SIMPLE WAIT
    }
    public void openSite() {

        driver.get("https://www.asklaila.com/");
    }
    public void selectIndiaCard(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(IndiaCard));
        driver.findElement(IndiaCard).click();
    }

    public void selectChennaiCard(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ChennaiCard));
        driver.findElement(ChennaiCard).click();
    }

    public void searchService(String service) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));    // wait for search bar
        driver.findElement(searchInput).sendKeys(service);
        driver.findElement(searchInput).sendKeys(Keys.ENTER);
    }

}
