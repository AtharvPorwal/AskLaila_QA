package Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/*public class ScreenshotUtils {

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
} */

import java.nio.file.StandardCopyOption; // <-- add this import


public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String outputPath) {
        try {
            if (driver == null) {
                System.out.println("Screenshot Error: WebDriver is null.");
                return;
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Path.of(outputPath).toAbsolutePath();

            // Create parent folder if missing (only if there is a parent)
            Path parent = dest.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            // Overwrite existing file if present
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved at: " + dest);

        } catch (Exception e) {
            System.out.println("Screenshot Error: " + e.getMessage());
        }
    }
}