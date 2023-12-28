package poiAPI.office.samples.excel;

import jakarta.persistence.EntityManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.Session;

@RequiredArgsConstructor
public class TestVolumeXlsxSx {

    private final EntityManager entityManager;
    private final Session session;

    private record OwnershipRecord(String lastName, String firstName, String address, Integer percent,
                                   Integer percent1, Integer percent2, Integer percent3, Integer percent4, Integer percent5,
                                   Integer percent6, Integer percent7, Integer percent8, Integer percent9,
                                   String name1, String name2, String name3, String name4, String name5,
                                   String name6, String name7, String name8, String name9,
                                   String nick1, String nick2, String nick3, String nick4, String nick5) {
    }

    public void createFile(String fileLocation) throws IOException {

        System.out.println("createFile start " + fileLocation);

        List<TestVolumeXlsxSx.OwnershipRecord> ownershipQuery = session.createNativeQuery(
                        "select person.last_name, person.first_name, house.address, ownership.percent, " +
                                "ownership.percent1, ownership.percent2, ownership.percent3, ownership.percent4, ownership.percent5, " +
                                "ownership.percent6, ownership.percent7, ownership.percent8, ownership.percent9, " +
                                "ownership.name1, ownership.name2, ownership.name3, ownership.name4, ownership.name5, " +
                                "ownership.name6, ownership.name7, ownership.name8, ownership.name9, " +
                                "ownership.nick1, ownership.nick2, ownership.nick3, ownership.nick4, ownership.nick5 " +
                                " from poi_person person, poi_ownership ownership, poi_house house" +
                                " where ownership.person_id = person.id and ownership.house_id = house.id")
                .setTupleTransformer((tuples, aliases) -> {
                    TestVolumeXlsxSx.OwnershipRecord ownershipRecord = new TestVolumeXlsxSx.OwnershipRecord((String) tuples[0], (String) tuples[1], (String) tuples[2], (Integer) tuples[3],
                            (Integer) tuples[4], (Integer) tuples[5], (Integer) tuples[6], (Integer) tuples[7], (Integer) tuples[8],
                            (Integer) tuples[8], (Integer) tuples[10], (Integer) tuples[11], (Integer) tuples[12],
                            (String) tuples[13], (String) tuples[14], (String) tuples[15], (String) tuples[16], (String) tuples[17],
                            (String) tuples[18], (String) tuples[19], (String) tuples[20], (String) tuples[21],
                            (String) tuples[22], (String) tuples[23], (String) tuples[24], (String) tuples[25], (String) tuples[26]);
                    return ownershipRecord;
                })
                .getResultList();

        System.out.println("ownershipRecord");
        for (TestVolumeXlsxSx.OwnershipRecord ownershipRecord : ownershipQuery) {
            System.out.println(ownershipRecord.lastName);
            System.out.println("ownershipRecord.lastName");
        }

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("Ownership");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("lastName");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue("firstName");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(2);
        headerCell.setCellValue("address");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(3);
        headerCell.setCellValue("percent");
        headerCell.setCellStyle(headerStyle);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        CellStyle styleNoBold = workbook.createCellStyle();
        styleNoBold.setWrapText(true);
        CellStyle styleBold = workbook.createCellStyle();
        styleBold.setWrapText(true);
        CellStyle styleDouble = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        styleDouble.setDataFormat(format.getFormat("0.00"));
        styleDouble.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        styleDouble.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        BigDecimal bigDecimalbdOne = new BigDecimal(1.0d);
        BigDecimal bigDecimalFromDouble = new BigDecimal(0.1d);
        BigDecimal bigDecimalToSum = new BigDecimal(0.0d);
        XSSFFont styleBoldFont = (XSSFFont) workbook.createFont();
        styleBoldFont.setBold(true);
        styleBold.setFont(styleBoldFont);
        int i = 1;
        for (int j = 0; j < 50000; ++j) {
            for (TestVolumeXlsxSx.OwnershipRecord ownershipRecord : ownershipQuery) {
                ++i;
                if ((i % 10) == 0) {
                    style = styleBold;
                } else {
                    style = styleNoBold;
                }
                bigDecimalFromDouble = bigDecimalFromDouble.add(bigDecimalbdOne);
                bigDecimalToSum = bigDecimalToSum.add(bigDecimalFromDouble);
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(ownershipRecord.lastName);
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(ownershipRecord.firstName);
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(ownershipRecord.address);
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(ownershipRecord.percent);
                cell.setCellStyle(style);
                cell = row.createCell(4);
                applyNumericFormat(cell, ownershipRecord.percent1.doubleValue(), styleDouble);
                cell = row.createCell(5);
                applyNumericFormat(cell, ownershipRecord.percent2.doubleValue(), styleDouble);
                cell = row.createCell(6);
                applyNumericFormat(cell, ownershipRecord.percent3.doubleValue(), styleDouble);
                cell = row.createCell(7);
                applyNumericFormat(cell, ownershipRecord.percent4.doubleValue(), styleDouble);
                cell = row.createCell(8);
                applyNumericFormat(cell, ownershipRecord.percent5.doubleValue(), styleDouble);
                cell = row.createCell(9);
                applyNumericFormat(cell, ownershipRecord.percent6.doubleValue(), styleDouble);
                cell = row.createCell(10);
                applyNumericFormat(cell, ownershipRecord.percent7.doubleValue(), styleDouble);
                cell = row.createCell(11);
                applyNumericFormat(cell, ownershipRecord.percent8.doubleValue(), styleDouble);
                cell = row.createCell(12);
                applyNumericFormat(cell, ownershipRecord.percent9.doubleValue(), styleDouble);
                cell = row.createCell(13);
                cell.setCellValue(ownershipRecord.name1);
                cell.setCellStyle(style);
                cell = row.createCell(14);
                cell.setCellValue(ownershipRecord.name2);
                cell.setCellStyle(style);
                cell = row.createCell(15);
                cell.setCellValue(ownershipRecord.name3);
                cell.setCellStyle(style);
                cell = row.createCell(16);
                cell.setCellValue(ownershipRecord.name4);
                cell.setCellStyle(style);
                cell = row.createCell(17);
                cell.setCellValue(ownershipRecord.name5);
                cell.setCellStyle(style);
                cell = row.createCell(18);
                cell.setCellValue(ownershipRecord.name6);
                cell.setCellStyle(style);
                cell = row.createCell(19);
                cell.setCellValue(ownershipRecord.name7);
                cell.setCellStyle(style);
                cell = row.createCell(20);
                cell.setCellValue(ownershipRecord.name8);
                cell.setCellStyle(style);
                cell = row.createCell(21);
                cell.setCellValue(ownershipRecord.name9);
                cell.setCellStyle(style);
                cell = row.createCell(22);
                cell.setCellValue(ownershipRecord.nick1);
                cell.setCellStyle(style);
                cell = row.createCell(23);
                cell.setCellValue(ownershipRecord.nick2);
                cell.setCellStyle(style);
                cell = row.createCell(24);
                cell.setCellValue(ownershipRecord.nick3);
                cell.setCellStyle(style);
                cell = row.createCell(25);
                cell.setCellValue(ownershipRecord.nick4);
                cell.setCellStyle(style);
                cell = row.createCell(26);
                cell.setCellValue(ownershipRecord.nick5);
                cell.setCellStyle(style);
                cell = row.createCell(27);
                cell.setCellValue(bigDecimalFromDouble.toString());
                cell.setCellStyle(style);
            }
            ++i;
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(27);
            cell.setCellValue(bigDecimalToSum.toString());
            cell.setCellStyle(style);
        }
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        System.out.println("before workbook.write(outputStream) ");
        workbook.write(outputStream);
        outputStream.close();
        System.out.println("createFile end " + fileLocation);
        workbook.close();
        workbook.dispose();
    }

    private static void applyNumericFormat(Cell cell, Double value, CellStyle style) {
        cell.setCellValue(value + 0.1);
        cell.setCellStyle(style);
    }


}

