package poiAPI.integration;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import poiAPI.dto.PersonDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonAPIIntegrationTest {
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
    void testControllerFindAllPersons() {
        String url = baseUrl + "/persons";
        ResponseEntity<PersonDto[]> response = restTemplate.getForEntity(url, PersonDto[].class);
        log.info("Список Person: " + response);
        for (int i = 0; i < response.getBody().length; ++i) {
            log.info(response.getBody()[i].toString());
        }
    }

    @Test
    @Order(2)
    void testControllerFindPersonById() {
        String url = baseUrl + "/persons/1";
        ResponseEntity<PersonDto> response = restTemplate.getForEntity(url, PersonDto.class);
        log.info("Запрос GET на вывод данных по id 1 " + response);
        log.info(response.getBody().toString());
    }

    @Test
    @Order(3)
    void testControllerCreatePerson() {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/persons")
                .queryParam("lastName", "Трапезников")
                .queryParam("firstName", "Виктор")
                .queryParam("age", 37)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<PersonDto> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>("", new HttpHeaders()),
                PersonDto.class
        );
        log.info("Запрос POST на создание Person" + response);
        log.info(response.getBody().toString());
    }

    @Test
    @Order(4)
    void testControllerUpdatePerson() {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/persons/3")
                .queryParam("lastName", "Фонвизин")
                .queryParam("firstName", "Виктор")
                .queryParam("age", 37)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        ResponseEntity<PersonDto> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                new HttpEntity<>("", new HttpHeaders()),
                PersonDto.class
        );
        log.info("Запрос POST на обновление записи Person " + response);
        log.info(response.getBody().toString());
    }

    @Test
    @Order(5)
    void testControllerDeletePerson() {
        String url = baseUrl + "/persons/3";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>("", new HttpHeaders()),
                String.class
        );
        log.info("Запрос DELETE на удаление записи Person " + response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}
