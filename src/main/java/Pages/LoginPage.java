package Pages;

//import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver , this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    @FindBy(id = "loginEmail")
    private WebElement email;

    @FindBy(id = "loginPassword")
    private WebElement password;

    @FindBy(id = "loginRes")
    private WebElement loginBtn;

    @FindBy(id = "errdiv")
    private WebElement errorMessage;

    By closeBtn = By.xpath("//button[@class='close']");


    public void setEmail(String value) {

        email.clear();
        email.sendKeys(value);
    }

    public void clearEmail() {
        email.clear();
   }

    public void setPassword(String value) {

        email.clear();
        password.clear();
        password.sendKeys(value);
    }

    public void clickLogin() {
        loginBtn.click();
    }

    /** Error text from site's error block (if any). Returns empty if not present yet. */
    public String getError() {
        try {
            String text = errorMessage.getText();
            if (text.isBlank()) {
                Thread.sleep(3000);
                text = errorMessage.getText();
            }
            return text;
        } catch (Exception e) {
            return "";
        }
    }

    /** Browser-native tooltip for invalid email (HTML5 validationMessage) */
    public String getEmailValidationTooltip() {
        email.sendKeys(Keys.TAB); // blur to trigger native validation
        return email.getAttribute("validationMessage");
    }

    public void closePopUp(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(closeBtn));
        driver.findElement(closeBtn).click();
    }
}
