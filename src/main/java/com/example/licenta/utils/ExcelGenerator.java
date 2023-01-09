package com.example.licenta.utils;

import com.example.licenta.model.SoliciareAcord;
import com.example.licenta.model.dto.StudentStatusDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    private List<StudentStatusDto> studentStatus;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<StudentStatusDto> studentStatus) {
        this.studentStatus = studentStatus;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Student status");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Student Id", style);
        createCell(row, 1, "Student Name", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Acord URL", style);
        createCell(row, 4, "Acord time", style);
        createCell(row, 5, "Coordinator Id", style);
        createCell(row, 6, "Coordinator Name", style);
        createCell(row, 7, "Email", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (var entry : studentStatus) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            SoliciareAcord lastAgreement = null;
            if (!entry.agreementRequests().isEmpty()) {
                lastAgreement = entry.agreementRequests().get(entry.agreementRequests().size() - 1);
            }
            createCell(row, columnCount++, entry.user().getId(), style);
            createCell(row, columnCount++, entry.user().getFirstName() + " " + entry.user().getLastName(), style);
            createCell(row, columnCount++, entry.user().getEmail(), style);
            if (lastAgreement != null) {
                createCell(row, columnCount++, lastAgreement.getDocumentUrl(), style);
                createCell(row, columnCount++, lastAgreement.getTime().toLocalTime().toString(), style);
            } else {
                createCell(row, columnCount++, "No agreement yet!", style);
                createCell(row, columnCount++, "No agreement yet!", style);
            }
            if (entry.coordinator() != null) {
                createCell(row, columnCount++, entry.coordinator().getId(), style);
                createCell(row, columnCount++, entry.coordinator().getFirstName() + " " + entry.coordinator().getLastName(), style);
                createCell(row, columnCount++, entry.coordinator().getEmail(), style);
            } else {
                createCell(row, columnCount++, "No coordinator yer", style);
                createCell(row, columnCount++, "No coordinator yer", style);
                createCell(row, columnCount++, "No coordinator yer", style);
            }
        }
    }

    public XSSFWorkbook generateExcelFile() {
        writeHeader();
        write();

        return workbook;
    }
}