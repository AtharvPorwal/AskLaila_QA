package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Fitness_Centres {
    private WebDriver driver;
    private WebDriverWait wait;

//    @FindBy(xpath="//span[@title='India']")
//    WebElement india;
//
//    @FindBy(xpath = "//h2[text()=' Chennai']")
//    WebElement chennai;
//
    @FindBy(xpath = "//input[@title='Search']")
    WebElement serch_input;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement search_btn;

    @FindBy(xpath = "//h1[text()='Top fitness centres in Chennai']")
    WebElement validate_fitness_centres;

    @FindBy(xpath = "//div[@id='searchCategory']//button")
    WebElement category_filter;

    @FindBy(xpath = "//ul[@class='dropdown-menu']//li//a[@title='fitness centres in Gym']")
    WebElement gym_option;

    @FindBy(xpath = "//span[text()='(Filtered by Gym)']")
    WebElement gym_text;

    @FindBy(xpath = "//div[@id='searchSubCategory']//button")
    WebElement sub_category_filter;

    @FindBy(xpath = "//ul[@class='dropdown-menu']//li//a[@title='Unisex Gym in  Chennai']")
    WebElement unisex_gym_option;

    @FindBy(xpath = "//h1[@class='catNameTitle']")
    WebElement unisex_gym_text;

    @FindBy(xpath = "//div[@class='cardContent']")
    List<WebElement> gym_cards;




    public Fitness_Centres(WebDriver driver)
    {
        this.driver=driver;
        this.wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

//    public void setcountry()
//    {
//        wait.until(ExpectedConditions.elementToBeClickable(india));
//        india.click();
//    }
//
//    public void setcity()
//    {
//        wait.until(ExpectedConditions.elementToBeClickable(chennai));
//        chennai.click();
//    }

    public void searchfitness()
    {
        wait.until(ExpectedConditions.elementToBeClickable(serch_input));
        serch_input.sendKeys("Fitness Centres");
        search_btn.click();
    }

    public String validate_fitness_page()
    {
        return validate_fitness_centres.getText();
    }

    public void setcategory()
    {
        wait.until(ExpectedConditions.elementToBeClickable(category_filter));
        category_filter.click();
        gym_option.click();
    }

    public String gym_page()
    {
        return gym_text.getText();
    }

    public void set_sub_category_filter()
    {
        wait.until(ExpectedConditions.elementToBeClickable(sub_category_filter));
        sub_category_filter.click();
        unisex_gym_option.click();
    }

    public String unisex_gym_page()
    {
        return unisex_gym_text.getText();
    }

    public List<WebElement> getGym_cards()
    {
        return gym_cards;
    }

    public List<String> getGym_information()
    {
        List<String> detailedList = new ArrayList<>();
        for(WebElement card : gym_cards)
        {
            String title = card.findElement(By.xpath(".//h2[@class='resultTitle']//a")).getText();
            String address = card.findElement(By.xpath(".//i[@class='glyphicon glyphicon-map-marker']/parent::div")).getText();
            String phone = "NA";
            List<WebElement> phonenos = card.findElements(By.xpath(".//div[@class='cardElement']//a"));
            if(!phonenos.isEmpty())
            {
                phone=phonenos.get(0).getText();
            }

            detailedList.add("Gym: " + title + " | Address: " + address + " | Phone: " + phone);

        }

        return detailedList;
    }
}
