package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** Represents the AskLaila results page showing multiple "cards". */
public class ResultsPage {

    private final WebDriver driver;
    WebDriverWait wait;

    By homePage = By.xpath("(//span[@class='lailaLogo'])[1]");
    By location = By.xpath("//input[@title='Locality']");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // One card per listing:
    private By cardRoots() {
        // Each listing sits inside .cardWrap > .card
        return By.cssSelector("div.col-md-6.cardWrap > div.card");
    }

    /** Returns first N cards present (top-to-bottom, left-to-right). */
    public List<ListingCard> getTopCards(int count) {
        List<WebElement> roots = driver.findElements(cardRoots());
        List<ListingCard> out = new ArrayList<>();
        for (int i = 0; i < count && i < roots.size(); i++) {
            out.add(new ListingCard(roots.get(i)));
        }
        return out;
    }

    public void returnHome(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePage));
        driver.findElement(homePage).click();
    }

    public void clearLocation(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(location));
        driver.findElement(location).clear();
    }
}