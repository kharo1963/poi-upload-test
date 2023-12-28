package poiAPI.office.samples.excel;

import jakarta.persistence.EntityManager;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import poiAPI.model.Person;

@RequiredArgsConstructor
public class HibernateCursorXlsxSx {
    private final EntityManager entityManager;
    private final Session session;

    public void createFile(String fileLocation) throws IOException {

        ScrollableResults scrollableResults = null;
        scrollableResults = session
                .createQuery("select p from Person p")
                .setCacheMode(CacheMode.IGNORE)
                .scroll(ScrollMode.FORWARD_ONLY);

        int count = 0;
        while (scrollableResults.next()) {
            Person person = (Person) scrollableResults.get();
            System.out.println(person.getLastName());
        }
  }

}
