package poiAPI.office.samples.pdf;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import jakarta.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.hibernate.Session;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

@RequiredArgsConstructor
public class PersonsPdf {

    private final EntityManager entityManager;
    private final Session session;

    private record OwnershipRecord(String lastName, String firstName, String address, Integer percent) {
    }

    public void createFile(String fileLocation) throws Exception {

        List<OwnershipRecord> ownershipQuery = session.createNativeQuery(
                        "select person.last_name, person.first_name, house.address, ownership.percent" +
                                " from poi_person person, poi_ownership ownership, poi_house house" +
                                " where ownership.person_id = person.id and ownership.house_id = house.id")
                .setTupleTransformer((tuples, aliases) -> {
                    OwnershipRecord ownershipRecord = new OwnershipRecord((String) tuples[0], (String) tuples[1], (String) tuples[2], (Integer) tuples[3]);
                    return ownershipRecord;
                })
                .getResultList();

        XWPFDocument document = new XWPFDocument();
        // Create a new section
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("lastName firstName address percent");

        int i = 1;
        for (OwnershipRecord ownershipRecord : ownershipQuery) {
            ++i;
            para = document.createParagraph();
            run = para.createRun();
            run.setText(ownershipRecord.lastName + " " + ownershipRecord.firstName + " " + ownershipRecord.address  + " " + ownershipRecord.percent);
            run.setFontSize(10);
            run.setFontFamily("Arial");
        }

        XWPFStyles styles = document.createStyles();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        CTPageSz pageSize = sectPr.addNewPgSz();
        pageSize.setW(BigInteger.valueOf(12240)); // 8.5 inches in twips
        pageSize.setH(BigInteger.valueOf(15840)); // 11 inches in twips

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        document.close();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        XWPFDocument toPdfDocument = new XWPFDocument(byteArrayInputStream);
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        PdfOptions pdfOptions = PdfOptions.create();
        PdfConverter.getInstance().convert(toPdfDocument, outputStream, pdfOptions);
        outputStream.close();
        toPdfDocument.close();
    }

}
