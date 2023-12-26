package poiAPI.integration;

import java.io.File;
import java.io.FileOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PoiAPIIntegrationTest {
    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;
    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port;
        log.info("baseUrl: {}", baseUrl);
    }

    @Test
    @Order(1)
    void testXlsxFileCreate() {
        String url = baseUrl + "/files/" + "person.xlsx";
        File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("person.xlsx");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        log.info("File created: " + file.getName() + file.getAbsolutePath());
        assertNotNull(file);
    }

    @Test
    @Order(2)
    void testDocxFileCreate() {
        String url = baseUrl + "/files/" + "person.docx";
        File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("person.docx");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        log.info("File created: " + file.getName() + file.getAbsolutePath());
        assertNotNull(file);
    }

    @Test
    @Order(3)
    void testPdfFileCreate() {
        String url = baseUrl + "/files/" + "person.pdf";
        File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("person.pdf");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        log.info("File created: " + file.getName() + file.getAbsolutePath());
        assertNotNull(file);
    }

    @Test
    @Order(4)
    void testXlsxFileVolume() {
        String url = baseUrl + "/files/" + "test_volume_50.xlsx";
        File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("test_volume_50.xlsx");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        log.info("File created: " + file.getName() + file.getAbsolutePath());
        assertNotNull(file);
    }

}
