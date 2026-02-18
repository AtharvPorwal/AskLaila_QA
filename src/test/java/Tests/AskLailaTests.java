package Tests;
import Pages.*;
import Utils.ExcelUtils;
import Utils.Gym_ExcelUtils;
import Utils.ScreenshotUtils;
import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AskLailaTests extends BaseTest {

    @Test
    public void searchCarWash() {
        HomePage home = new HomePage(driver);
        home.openSite();
        home.selectIndiaCard();
        home.selectChennaiCard();
        home.searchService("Car Washing Services");

        CWSPage cws = new CWSPage(driver);
        cws.clickCategory();
        cws.clickCategoryOptions();
        cws.clickLocalities();
        cws.clickLocalitiesOptions();
        cws.clickSub();
        cws.clickSubOptions();


        /* ResultsPage results = new ResultsPage(driver);
        List<ListingCard> top5 = results.getTopFiveCards();

        System.out.println("\n===== TOP 5 RESULTS =====");
        int i = 1;
        for (ListingCard card : top5) {
            System.out.println("----- Card " + i + " -----");
            System.out.println("Name: " + card.getName());
            System.out.println("Details: " + card.getDetails());
            i++;
        } */

        //ResultsPage results = new ResultsPage(driver);
        //var top5 = results.getTopCards(5);

        ScreenshotUtils.takeScreenshot(driver, "Screenshots/ResultPage.png");


        ResultsPage results = new ResultsPage(driver);
        List<ListingCard> top5 = results.getTopCards(5);

// SAVE EXCEL
        ExcelUtils.write(top5, "src/main/java/Utils/CarWashResults.xlsx");


        /* System.out.println("\n===== TOP 5 RESULTS =====");
        int i = 1;
        for (ListingCard card : top5) {
            System.out.println("----- Card " + i + " -----");
            System.out.println("Name : " + card.getName());
            System.out.println("Phone: " + card.getPhoneOrNA());  // <-- will print N/A if no number
            System.out.println("Details:\n" + card.getDetails());
            i++;
        }
 */


        System.out.println("\n===== TOP 5 CARDS =====");
        int i = 1;
        for (ListingCard card : top5) {
            System.out.println("----- Card " + i + " -----");
            System.out.println("Name       : " + card.getName());
            System.out.println("URL        : " + card.getListingUrl());
            System.out.println("Category   : " + card.getCategory());
            System.out.println("Phones     : " + card.getPhonesOrNA());   // If no numbers → [N/A]
            System.out.println("Address    : " + card.getAddressOrNA());
            System.out.println("Services   : " + card.getServicesOrNA());
            System.out.println("Recommend% : " + card.getRecommendationPercentOrNA());
            i++;


        }
        results.returnHome();
        results.clearLocation();

    }

    @Test(dependsOnMethods = {"searchCarWash"})
    public void verifyLoginErrors() throws InterruptedException {

        // Flow to reach login
        FreeListing fl = new FreeListing(driver);
        fl.clickFreeListing();
        fl.clickContinue();

        LoginPage login = new LoginPage(driver);

        // 1) Invalid Email -> native tooltip + screenshot
        login.setEmail("sdfg9");
        String tooltip = login.getEmailValidationTooltip();
        login.clickLogin();
        Thread.sleep(3000);
        System.out.println("Tooltip (invalid email): " + tooltip);
        String err1 = login.getError();

        System.out.println("Error 1 (invalid email): " + err1);
        ScreenshotUtils.takeScreenshot(driver, "Screenshots/invalidEmail.png");

        // 2) Valid email, no password -> site err div + screenshot
        login.clearEmail();
        login.setEmail("test@gmail.com");
        login.clickLogin();
        Thread.sleep(3000);
        String err2 = login.getError();
        System.out.println("Error 2 (no password): " + err2);
        ScreenshotUtils.takeScreenshot(driver, "Screenshots/noPassword.png");

        // 3) Email + invalid password -> site err div + screenshot
        login.clearEmail();
        //login.setEmail("test@gmail.com");
        login.setPassword("wrongpass");
        login.clickLogin();
        Thread.sleep(3000);
        String err3 = login.getError();
        System.out.println("Error 3 (invalid password): " + err3);
        ScreenshotUtils.takeScreenshot(driver, "Screenshots/invalidPassword.png");

        login.closePopUp();

        ResultsPage resultsPage = new ResultsPage(driver);
        resultsPage.returnHome();
        resultsPage.clearLocation();


        ResultsPage resultsPage1 = new ResultsPage(driver);
        resultsPage1.returnHome();
        Thread.sleep(5000);

    }


    @Test(dependsOnMethods = {"verifyLoginErrors"})
    public void fitnessCenter() throws IOException {
        Fitness_Centres fc = new Fitness_Centres(driver);
        fc.searchfitness();
        //System.out.println("Setting Category to Gym");
        fc.setcategory();
        System.out.println("Setting Sub Category to Unisex Gym");
        fc.set_sub_category_filter();
        List<WebElement> gym_cards = fc.getGym_cards();
        System.out.println("Total Gyms: " + gym_cards.size());
        System.out.println("\n--- PRINTING AND STORING GYM LIST ---");
        List<String> gymDataList = fc.getGym_information();

        // Preparation for Excel
        List<String[]> excelData = new ArrayList<>();
        String excelPath = System.getProperty("user.dir") +"\\AskLaila_QA\\src\\main\\java\\Utils\\Gymdata.xlsx";
        Gym_ExcelUtils excel = new Gym_ExcelUtils(excelPath);

        for (String record : gymDataList) {
            System.out.println(record); // Printing to Console

            // Parsing: "Gym: Name | Address: Addr | Phone: Num"
            String[] parts = record.split(" \\| ");
            String name = parts[0].replace("Gym: ", "");
            String addr = parts[1].replace("Address: ", "");
            String phone = parts[2].replace("Phone: ", "");
            excelData.add(new String[]{name, addr, phone});
        }

        // Store in Excel
        String[] headers = {"Gym Name", "Address", "Phone Number"};
        excel.writeGymDataToNewSheet("ChennaiGyms", headers, excelData);

        ScreenshotUtils.takeScreenshot(driver, "Screenshots/FitnessResults.png");

        ResultsPage resultsPage1 = new ResultsPage(driver);
       resultsPage1.returnHome();






    }
}

//"C:\Users\2461940\OneDrive - Cognizant\Desktop\Mini Project\AskLaila_QA\src\main\java\Utils"
