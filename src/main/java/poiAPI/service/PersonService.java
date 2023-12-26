package poiAPI.service;

import java.io.IOException;
import java.util.List;
import poiAPI.dto.PersonDto;

public interface PersonService {
    List<PersonDto> findAllPersons();

    PersonDto addPerson(String lastName, String firstName, Integer age);

    PersonDto updatePerson(Integer personId, String lastName, String firstName, Integer age);

    PersonDto findPersonById(Integer personId);

    void deletePerson(Integer personId);
}
