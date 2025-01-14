package corp.product.util;

import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import corp.product.dto.ReportDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExcelReportForTime {
    public static ByteArrayInputStream toFile(List<List<ReportDto>> list,
                                              LocalDate startDate,
                                              LocalDate endDate) throws IOException {
        String start = startDate.toString();
        String end = endDate.toString();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products" + start + "-" + end);

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


            Cell cell = row.createCell(0);
            cell.setCellValue("Customer");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Product name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Variant");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Quantity");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Side");
            cell.setCellStyle(headerCellStyle);

            int numRow = 1;
            for (int i = 0; i < list.size(); i++) {
                List<ReportDto> reportDtos = list.get(i);
                Row title = sheet.createRow(numRow++);
                title.createCell(0).setCellValue(reportDtos.getFirst().getLineName());
                for (int j = 0; j < reportDtos.size(); j++) {
                    Row dataRow = sheet.createRow(numRow++);
                    ReportDto reportDto = reportDtos.get(j);
                    dataRow.createCell(0).setCellValue(reportDto.getNameOrg());
                    dataRow.createCell(1).setCellValue(reportDto.getNamePr());
                    dataRow.createCell(2).setCellValue(reportDto.getVar());
                    dataRow.createCell(3).setCellValue(reportDto.getQuantity());
                    dataRow.createCell(4).setCellValue(reportDto.getSide());
                }
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
