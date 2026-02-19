package Listeners;

import Base.BaseTest;
import Utils.ExtentManager;
import Utils.ExtentTestManager;
import com.aventstack.extentreports.*;
//import com.aventstack.extentreports;
import org.openqa.selenium.*;
import org.testng.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentTestNGListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;

    @Override
    public void onStart(ISuite suite) {
        extent = ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentTest test = extent.createTest(testName, description != null ? description : "");
        ExtentTestManager.setTest(test);

        // Optional: add category from groups
        for (String g : result.getMethod().getGroups()) {
            test.assignCategory(g);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test passed");
        ExtentTestManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentTestManager.getTest();
        test.fail(result.getThrowable());

        // Try to capture screenshot if driver is available
        try {
            WebDriver driver = ((BaseTest) result.getInstance()).driver;
            String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                test.fail("Screenshot on failure",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        } catch (Exception e) {
            test.warning("Failed to capture screenshot: " + e.getMessage());
        }

        ExtentTestManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip("Test skipped");
        ExtentTestManager.unload();
    }

    // Utility for screenshots (unique filename)
    private String takeScreenshot(WebDriver driver, String methodName) {
        try {
            if (!(driver instanceof TakesScreenshot)) return null;

            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String dir = System.getProperty("user.dir") + "/Screenshots/";
            Files.createDirectories(Paths.get(dir));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = dir + methodName + "_" + ts + ".png";
            Files.copy(src.toPath(), Paths.get(path));
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}
