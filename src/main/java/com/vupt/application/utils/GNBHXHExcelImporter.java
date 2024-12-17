package com.vupt.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vupt.application.model.GiayNghiBHXHDetail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GNBHXHExcelImporter {
    public static final int COLUMN_INDEX_STT = 0;
    public static final int COLUMN_INDEX_MACT = 1;
    public static final int COLUMN_INDEX_SOKCB = 2;
    public static final int COLUMN_INDEX_MABV = 3;
    public static final int COLUMN_INDEX_MABS = 4;
    public static final int COLUMN_INDEX_MASOBHXH = 5;
    public static final int COLUMN_INDEX_MATHE = 6;
    public static final int COLUMN_INDEX_HOTEN = 7;
    public static final int COLUMN_INDEX_NGAYSINH = 8;
    public static final int COLUMN_INDEX_GIOITINH = 9;
    public static final int COLUMN_INDEX_PPDIEUTRI = 10;
    public static final int COLUMN_INDEX_MADVI = 11;
    public static final int COLUMN_INDEX_TENDVI = 12;
    public static final int COLUMN_INDEX_TUNGAY = 13;
    public static final int COLUMN_INDEX_DENNGAY = 14;
    public static final int COLUMN_INDEX_SONGAY = 15;
    public static final int COLUMN_INDEX_HOTENCHA = 16;
    public static final int COLUMN_INDEX_HOTENME = 17;
    public static final int COLUMN_INDEX_NGAYCT = 18;
    public static final int COLUMN_INDEX_NGUOIDAIDIEN = 19;
    public static final int COLUMN_INDEX_TENBSY = 20;
    public static final int COLUMN_INDEX_SERI = 21;
    public static final int COLUMN_INDEX_MAUSO = 22;


    public static List<GiayNghiBHXHDetail> readExcel(String excelFilePath) throws IOException {
        List<GiayNghiBHXHDetail> giayNghiBHXHDetails = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
            if (nextRow.getCell(0) == null || nextRow.getCell(0).toString().isEmpty()) continue;

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            GiayNghiBHXHDetail giayNghiBHXHDetail = new GiayNghiBHXHDetail();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_STT:
                        giayNghiBHXHDetail.setSTT(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MACT:
                        giayNghiBHXHDetail.setMA_CT(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_SOKCB:
                        giayNghiBHXHDetail.setSO_KCB(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MABV:
                        giayNghiBHXHDetail.setMA_BV(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MABS:
                        giayNghiBHXHDetail.setMA_BS(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MASOBHXH:
                        giayNghiBHXHDetail.setMA_SOBHXH(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MATHE:
                        giayNghiBHXHDetail.setMA_THE(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_HOTEN:
                        giayNghiBHXHDetail.setHO_TEN((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_NGAYSINH:
                        giayNghiBHXHDetail.setNGAY_SINH(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_GIOITINH:
                        giayNghiBHXHDetail.setGIOI_TINH(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_PPDIEUTRI:
                        giayNghiBHXHDetail.setPP_DIEUTRI(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MADVI:
                        giayNghiBHXHDetail.setMA_DVI(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_TENDVI:
                        giayNghiBHXHDetail.setTEN_DVI(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_TUNGAY:
                        giayNghiBHXHDetail.setTU_NGAY(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_DENNGAY:
                        giayNghiBHXHDetail.setDEN_NGAY(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_SONGAY:
                        giayNghiBHXHDetail.setSO_NGAY(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_HOTENCHA:
                        giayNghiBHXHDetail.setHOTEN_CHA(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_HOTENME:
                        giayNghiBHXHDetail.setHOTEN_ME(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_NGAYCT:
                        giayNghiBHXHDetail.setNGAY_CT(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_NGUOIDAIDIEN:
                        giayNghiBHXHDetail.setNGUOI_DAI_DIEN(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_TENBSY:
                        giayNghiBHXHDetail.setTEN_BSY(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_SERI:
                        giayNghiBHXHDetail.setSERI(getCellValue(cell));
                        break;
                    case COLUMN_INDEX_MAUSO:
                        giayNghiBHXHDetail.setMAU_SO(getCellValue(cell));
                        break;
                    default:
                        break;
                }

            }
            giayNghiBHXHDetails.add(giayNghiBHXHDetail);
        }

        workbook.close();
        inputStream.close();

        return giayNghiBHXHDetails;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}

