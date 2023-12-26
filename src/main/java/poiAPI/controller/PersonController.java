package poiAPI.controller;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import poiAPI.dto.PersonDto;
import poiAPI.service.PersonService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping()
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        log.info("Получен запрос GET на формирование списка всех Person");
        return new ResponseEntity<>(personService.findAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDto> findPersonById(@PathVariable() Integer personId) {
        log.info("Получен запрос GET на вывод данных по id {}, personId");
        return new ResponseEntity<>(personService.findPersonById(personId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PersonDto> addPerson(@RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("age") Integer age
    ) {
        log.info("Получен запрос POST на добавление записи Person " + lastName + " " + firstName + " " + age);
        return new ResponseEntity<>(personService.addPerson(lastName, firstName, age), HttpStatus.OK);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable() Integer personId,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("age") Integer age
    ) {
        log.info("Получен запрос PUT на обновление записи Person по id {}", personId);
        return new ResponseEntity<>(personService.updatePerson(personId, lastName, firstName, age), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{personId}")
    public ResponseEntity<?> delete(@PathVariable(name = "personId") Integer personId) {
        log.info("Получен запрос DELETE на удаление записи Person по id {}", personId);
        personService.deletePerson(personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
