package Base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils; // Requires Commons IO dependency
import java.io.File;
import java.io.IOException;



public class BaseTest {


    protected WebDriver driver;     // static: shared across test instances
    protected WebDriverWait wait;
    protected String browserName;

    @Parameters({"browser"})
    @BeforeSuite(alwaysRun = true)
    public void globalSetup(@Optional("chrome") String browser) {
        if (driver != null) return; // Already initialized

        browserName = browser.toLowerCase();

        switch (browserName) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 2);
                prefs.put("profile.managed_default_content_settings.popups", 2);
                prefs.put("profile.managed_default_content_settings.ads", 2);
                options.setExperimentalOption("prefs", prefs);

                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-notifications");
                options.addArguments("--no-default-browser-check");
                options.addArguments("--no-first-run");
                options.addArguments("--disable-infobars");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                options.setAcceptInsecureCerts(true);

                driver = new ChromeDriver(options);
                break;
            }


            case "edge": {
                EdgeOptions options = new EdgeOptions();

                // Similar prefs for Edge (Chromium-based)
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 2);
                prefs.put("profile.managed_default_content_settings.popups", 2);
                prefs.put("profile.managed_default_content_settings.ads", 2);
                options.setExperimentalOption("prefs", prefs);

                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-notifications");
                options.addArguments("--no-default-browser-check");
                options.addArguments("--no-first-run");
                options.addArguments("--disable-infobars");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                options.setAcceptInsecureCerts(true);

                driver = new EdgeDriver(options);
                break;
            }

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.get("https://www.asklaila.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Inside BaseTest class
    public String takeScreenshot(String testName) {
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }

    // Uncomment if you want to close browser after the class
    // @AfterClass(alwaysRun = true)
    // public void tearDown() {
    //     if (driver != null) {
    //         driver.quit();
    //     }
    // }
}