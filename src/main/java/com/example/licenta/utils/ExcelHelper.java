package com.example.licenta.utils;

import com.example.licenta.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String[] HEADERS = {"firstName", "lastName", "email", "password", "role"};
    public static final String SHEET = "Users";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }


    public static List<User> excelToUsers(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<User> tutorials = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                User user = new User();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1 -> user.setFirstName(currentCell.getStringCellValue());
                        case 2 -> user.setLastName(currentCell.getStringCellValue());
                        case 3 -> user.setEmail(currentCell.getStringCellValue());
                        case 4 -> user.setPassword(currentCell.getStringCellValue());
                        case 5 -> user.setRole(currentCell.getStringCellValue());
                    }

                    cellIdx++;
                }

                tutorials.add(user);
            }

            workbook.close();

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
