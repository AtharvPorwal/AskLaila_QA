package Utils;

import Pages.ListingCard;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtils {

    // SIMPLE EXCEL WRITER
    public static void write(List<ListingCard> cards, String path) {
        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Cards");

            // HEADER ROW
            Row h = sheet.createRow(0);
            h.createCell(0).setCellValue("Name");
            h.createCell(1).setCellValue("URL");
            h.createCell(2).setCellValue("Category");
            h.createCell(3).setCellValue("Phones");
            h.createCell(4).setCellValue("Address");
            h.createCell(5).setCellValue("Services");
            h.createCell(6).setCellValue("Recommend");

            // DATA ROWS
            int r = 1;
            for (ListingCard c : cards) {
                Row row = sheet.createRow(r++);

                row.createCell(0).setCellValue(c.getName());
                row.createCell(1).setCellValue(c.getListingUrl());
                row.createCell(2).setCellValue(c.getCategory());
                row.createCell(3).setCellValue(String.join(", ", c.getPhonesOrNA()));
                row.createCell(4).setCellValue(c.getAddressOrNA());
                row.createCell(5).setCellValue(c.getServicesOrNA());
                row.createCell(6).setCellValue(c.getRecommendationPercentOrNA());
            }

            // SAVE FILE
            FileOutputStream out = new FileOutputStream(path);
            wb.write(out);
            out.close();

            System.out.println("Excel created: " + path);

        } catch (Exception e) {
            System.out.println("ERROR writing Excel: " + e.getMessage());
        }
    }
}