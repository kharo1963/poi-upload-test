package poiAPI.office.samples.excel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import poiAPI.model.Person;
import poiAPI.repository.PersonRepository;

@RequiredArgsConstructor
public class PersonsXlsx {

    private final EntityManager entityManager;
    private final Session session;
    private final PersonRepository personRepository;

    public void createFile(String fileLocation) throws IOException {

        List<Person> persons = personRepository.findByLastNameInOrderByFirstNameDesc(List.of("Сидоров","Петров"));
        System.out.println("persons");
        for (Person person : persons) {
            System.out.println(person.getLastName() + " " + person.getFirstName());
        }

        List<String> lastNamesM0 = entityManager.createQuery("select distinct lastName from Person")
                .getResultList();
        System.out.println("lastNamesM0 " + lastNamesM0);

        List<String> lastNamesS0 = session.createQuery("select distinct lastName from Person")
                .getResultList();
        System.out.println("lastNamesS0 " + lastNamesS0);
        List<Person> lastNamesM1 = entityManager.createNativeQuery("SELECT * FROM poi_person", Person.class)
                .getResultList();
        System.out.println("lastNamesM1 " + lastNamesM1);

        List<Person> lastNamesS1 = session.createNativeQuery("SELECT * FROM poi_person", Person.class)
                .getResultList();
        System.out.println("lastNamesS1 " + lastNamesS1);

        List<Person> lastNamesM2 = entityManager.createQuery("select lastName from Person where firstName like :nameMask")
                .setParameter("nameMask", "%а%")
                .getResultList();
        System.out.println("lastNamesM2 " + lastNamesM2);

        List<Tuple> ownershipQuery = entityManager.createQuery("select person.lastName, person.firstName, house.address, ownership.percent" +
                        " from Person person, Ownership ownership, House house" +
                        " where ownership.personId = person.id and ownership.houseId = house.id", Tuple.class)
                .getResultList();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ownership");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
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
        int i = 1;
        for (Tuple tuple : ownershipQuery) {
            ++i;
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(tuple.get(0, String.class));
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(tuple.get(1, String.class));
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(tuple.get(2, String.class));
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(tuple.get(3, Integer.class));
            cell.setCellStyle(style);
        }
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

}
