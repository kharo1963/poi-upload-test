package poiAPI.office.samples.doc;

import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PersonsDocx {

    public void createFile(String fileLocation) throws Exception {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("apache.poi example");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);
        for (int i = 0; i < 25; ++i) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            String paragraphString = "Петр Сидоров " + i;
            XWPFRun paragraphRun = paragraph.createRun();
            paragraphRun.setText(paragraphString);
        }
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        document.write(outputStream);
        outputStream.close();
        document.close();
    }

}

