package Tests;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Fitness_Centres;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class FitnessCentresTest extends BaseTest {

    private Fitness_Centres fc;
    private List<WebElement> gym_cards;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        fc = new Fitness_Centres(driver);
        gym_cards = fc.getGym_cards();
    }

    @Test(groups = {"smoke"})
    public void setLocation() {
        System.out.println("Setting Country to India");
        fc.setcountry();
        System.out.println("Setting City to Chennai");
        fc.setcity();
        Assert.assertEquals("AskLaila Chennai - India's local information service.", driver.getTitle());
    }

    @Test(groups = {"smoke"}, dependsOnMethods = {"setLocation"})
    public void search() {
        System.out.println("Searching for Fitness Centres");
        fc.searchfitness();
        Assert.assertEquals(fc.validate_fitness_page(), "Top fitness centres in Chennai");
    }

    @Test(groups = {"functional"}, dependsOnGroups = {"smoke"})
    public void setcategory() {
        System.out.println("Setting Category to Gym");
        fc.setcategory();
        Assert.assertEquals(fc.gym_page(), "(Filtered by Gym)");
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"setcategory"})
    public void set_sub_category() {
        System.out.println("Setting Sub Category to Unisex Gym");
        fc.set_sub_category_filter();
        Assert.assertEquals(fc.unisex_gym_page(), "Top unisex gym in Chennai");
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"set_sub_category"})
    public void locate_gym_cards() {
        Assert.assertFalse(gym_cards.isEmpty());
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"locate_gym_cards"})
    public void check_length_gym() {
        System.out.println("Total Gyms: " + gym_cards.size());
        Assert.assertTrue(gym_cards.size() > 0);
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"check_length_gym"})
    public void printAndStoreGymDetails() throws IOException {
        System.out.println("\n--- PRINTING AND STORING GYM LIST ---");
        List<String> gymDataList = fc.getGym_information();

        // Preparation for Excel
        List<String[]> excelData = new ArrayList<>();
        String excelPath = System.getProperty("user.dir") + "/GymData.xlsx";
        ExcelUtils excel = new ExcelUtils(excelPath);

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

        // Validation: Verify Excel data matches console/ArrayList data
        String firstGymNameFromExcel = excel.readCellData("ChennaiGyms", 1, 0);
        Assert.assertTrue(gymDataList.get(0).contains(firstGymNameFromExcel), "Excel validation failed!");
        System.out.println("Excel Data Validated: " + firstGymNameFromExcel);
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"printAndStoreGymDetails"})
    public void captureFinalResultsScreenshot() {
        String path = takeScreenshot("FitnessCentres_Final_Results");
        File screenshotFile = new File(path);
        Assert.assertTrue(screenshotFile.exists() && screenshotFile.length() > 0);
    }

    @Test(groups = {"functional"}, dependsOnMethods = {"captureFinalResultsScreenshot"})
    public void navigateToHome()
    {
        String excepted_title="AskLaila Chennai - India's local information service.";
        String actual_title;
        while (true)
        {
            actual_title=driver.getTitle();
            if(excepted_title.equals(actual_title))
            {
                break;
            }
            driver.navigate().back();
        }
    }


}