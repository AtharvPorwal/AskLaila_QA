package Utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Gym_ExcelUtils {
    private String filePath;

    public Gym_ExcelUtils(String filePath) {
        this.filePath = filePath;
    }

    public void writeGymDataToNewSheet(String sheetName, String[] headers, List<String[]> dataRows) throws IOException {
        Workbook workbook;
        File file = new File(filePath);

        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create or refresh sheet
        if (workbook.getSheet(sheetName) != null) {
            workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
        }
        Sheet sheet = workbook.createSheet(sheetName);

        // Header
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Data
        int rowNum = 1;
        for (String[] rowData : dataRows) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                row.createCell(i).setCellValue(rowData[i]);
            }
        }

        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    public String readCellData(String sheetName, int row, int col) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        String val = workbook.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
        workbook.close();
        fis.close();
        return val;
    }
}