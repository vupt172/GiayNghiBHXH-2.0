package com.vupt.application.utils;

import com.vupt.application.model.GiayNghiBHXHDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

public class GNBHXHExcelExporter {
    public static final int TOTAL_COL=8;
    public static final int COLUMN_INDEX_STT = 0;
    public static final int COLUMN_INDEX_MASOBHXH = 1;
    public static final int COLUMN_INDEX_MATHE = 2;
    public static final int COLUMN_INDEX_HOTEN = 3;
    public static final int COLUMN_INDEX_NGAYCT = 4;
    public static final int COLUMN_INDEX_NGUOIDAIDIEN = 5;
    public static final int COLUMN_INDEX_TENBSY = 6;
    public static final int COLUMN_INDEX_MAUSO = 7;
    public static void writeExcel(List<GiayNghiBHXHDto> giayNghiBHXHDtoList, LocalDate date, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Sheet1"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write title
        String title =GNBHXHUtils.getExcelTitle(date);
        writeTitleMergeCell(sheet,title,rowIndex++);
        // Write header
        writeHeader(sheet, rowIndex++);

        // Write data
        for (GiayNghiBHXHDto giayNghiBHXHDto : giayNghiBHXHDtoList) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeData(giayNghiBHXHDto, row);
            rowIndex++;
        }


        setColumnWidth(sheet);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Export excel done!!!");
    }




    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }
    //Write titled merge cell
    public static void writeTitleMergeCell(Sheet sheet,String title,int rowIndex) {
        CellStyle cellStyle = createStyleForTitle(sheet);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, TOTAL_COL-1));
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(cellStyle);
        rowIndex++;
    }
    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_STT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STT");

        cell = row.createCell(COLUMN_INDEX_MASOBHXH);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MA_SO_BHXH");

        cell = row.createCell(COLUMN_INDEX_MATHE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MA_THE");

        cell = row.createCell(COLUMN_INDEX_HOTEN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("HO_TEN");

        cell = row.createCell(COLUMN_INDEX_NGAYCT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NGAY_CT");

        cell = row.createCell(COLUMN_INDEX_NGUOIDAIDIEN);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NGUOI_DAI_DIEN");

        cell = row.createCell(COLUMN_INDEX_TENBSY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TEN_BAC_SY");

        cell = row.createCell(COLUMN_INDEX_MAUSO);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MAU_SO");
    }

    // Write data
    private static void writeData(GiayNghiBHXHDto giayNghiBHXHDto, Row row) {
        if (giayNghiBHXHDto.getSTT() == null) return;
        CellStyle cellStyle=createStyleForData(row.getSheet());
        Cell cell = row.createCell(COLUMN_INDEX_STT);
        cell.setCellValue(giayNghiBHXHDto.getSTT().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_MASOBHXH);
        cell.setCellValue(giayNghiBHXHDto.getMA_SOBHXH() == null ? "" : giayNghiBHXHDto.getMA_SOBHXH().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_MATHE);
        cell.setCellValue(giayNghiBHXHDto.getMA_THE() == null ? "" : giayNghiBHXHDto.getMA_THE().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_HOTEN);
        cell.setCellValue(giayNghiBHXHDto.getHO_TEN() == null ? "" : giayNghiBHXHDto.getHO_TEN());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_NGAYCT);
        cell.setCellValue(giayNghiBHXHDto.getNGAY_CT() == null ? "" : giayNghiBHXHDto.getNGAY_CT().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_NGUOIDAIDIEN);
        cell.setCellValue(giayNghiBHXHDto.getNGUOI_DAI_DIEN() == null ? "" : giayNghiBHXHDto.getNGUOI_DAI_DIEN().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_TENBSY);
        cell.setCellValue(giayNghiBHXHDto.getTEN_BSY() == null ? "" : giayNghiBHXHDto.getTEN_BSY().toString());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMN_INDEX_MAUSO);
        cell.setCellValue(giayNghiBHXHDto.getMAU_SO() == null ? "" : giayNghiBHXHDto.getMAU_SO().toString());
        cell.setCellStyle(cellStyle);
    }

    // Create CellStyle for title
    private static CellStyle createStyleForTitle(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    private static CellStyle createStyleForData(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }

    private static void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(COLUMN_INDEX_STT, 1200);
        sheet.setColumnWidth(COLUMN_INDEX_MASOBHXH, 3000);
        sheet.setColumnWidth(COLUMN_INDEX_MATHE, 4500);
        sheet.setColumnWidth(COLUMN_INDEX_HOTEN, 7000);
        sheet.setColumnWidth(COLUMN_INDEX_NGAYCT, 2500);
        sheet.setColumnWidth(COLUMN_INDEX_NGUOIDAIDIEN, 5500);
        sheet.setColumnWidth(COLUMN_INDEX_TENBSY, 5500);
        sheet.setColumnWidth(COLUMN_INDEX_MAUSO, 2000);
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

}
