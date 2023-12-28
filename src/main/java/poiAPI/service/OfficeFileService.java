package poiAPI.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import poiAPI.office.samples.doc.PersonsDocx;
import poiAPI.office.samples.excel.HibernateCursorXlsxSx;
import poiAPI.office.samples.excel.PersonsXlsx;
import poiAPI.office.samples.excel.PostgreCursorXlsxSx;
import poiAPI.office.samples.excel.TestVolumeXlsx;
import poiAPI.office.samples.excel.TestVolumeXlsxSx;
import poiAPI.office.samples.pdf.PersonsPdf;
import poiAPI.repository.PersonRepository;

@RequiredArgsConstructor
@Service
public class OfficeFileService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PersonRepository personRepository;

    public FileSystemResource createOoxmlFile(String fileName) {
        Session session = entityManager.unwrap(Session.class);
        try {
            String absolutePath = File.createTempFile(fileName, null).getAbsolutePath();
            FileSystemResource fileSystemResource = new FileSystemResource(absolutePath);
            if ("person.xlsx".equals(fileName)) {
                PersonsXlsx personsXlsx = new PersonsXlsx(entityManager, session, personRepository);
                personsXlsx.createFile(absolutePath);
            } else if ("person.docx".equals(fileName)) {
                PersonsDocx personsDocx = new PersonsDocx();
                personsDocx.createFile(absolutePath);
            } else if ("person.pdf".equals(fileName)) {
                PersonsPdf personsPdf = new PersonsPdf(entityManager, session);
                personsPdf.createFile(absolutePath);
            } else if (fileName.startsWith("test_volume_") && fileName.endsWith(".xlsx")) {
//                TestVolumeXlsx testVolumeXlsx = new TestVolumeXlsx(entityManager, session);
//                testVolumeXlsx.createFile(absolutePath);
                TestVolumeXlsxSx testVolumeXlsxSx = new TestVolumeXlsxSx(entityManager, session);
                testVolumeXlsxSx.createFile(absolutePath);
            } else if ("postgre_cursor.xlsx".equals(fileName)) {
                PostgreCursorXlsxSx postgreCursorXlsxSx = new PostgreCursorXlsxSx(entityManager, session);
                postgreCursorXlsxSx.createFile(absolutePath);
            } else if ("hibernate_cursor.xlsx".equals(fileName)) {
                HibernateCursorXlsxSx hibernateCursorXlsxSx = new HibernateCursorXlsxSx(entityManager, session);
                hibernateCursorXlsxSx.createFile(absolutePath);
            }
            if (fileSystemResource.exists() || fileSystemResource.isReadable()) {
                return fileSystemResource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
