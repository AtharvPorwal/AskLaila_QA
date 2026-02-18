package Pages;

//import Base.BaseTage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FreeListing  {
    WebDriver driver;
    WebDriverWait wait;


    public FreeListing(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver , this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    //super(driver);
    }

    @FindBy(id = "freeListing")
    private WebElement freeListingLink;

    @FindBy(xpath = "//div[@id='flowEntry']/button")
    private WebElement continueButton;

    public void clickFreeListing() {
        freeListingLink.click();
    }

    public void clickContinue() {

        continueButton.click();
    }
}