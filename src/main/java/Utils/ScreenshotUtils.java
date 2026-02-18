package Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String outputPath) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Path.of(outputPath).toAbsolutePath();

            // Create parent folder if missing
            Files.createDirectories(dest.getParent());

            Files.copy(src.toPath(), dest);
            System.out.println("Screenshot saved at: " + dest);

        } catch (Exception e) {
            System.out.println("Screenshot Error: " + e.getMessage());
        }
    }
}