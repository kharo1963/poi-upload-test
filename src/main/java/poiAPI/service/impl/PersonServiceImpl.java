package poiAPI.service.impl;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import poiAPI.dto.PersonDto;
import poiAPI.exception.DataNotFoundException;
import poiAPI.mapper.PersonMapper;
import poiAPI.model.Person;
import poiAPI.repository.PersonRepository;
import poiAPI.service.PersonService;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<PersonDto> findAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::mapToPersonDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto addPerson(String lastName, String firstName, Integer age) {
        Person person = new Person();
        person.setLastName(lastName);
        person.setFirstName(firstName);
        person.setAge(age);
        return PersonMapper.mapToPersonDto(personRepository.save(person));
    }

    @Override
    public PersonDto updatePerson(Integer personId, String lastName, String firstName, Integer age) {
        Person person = checkPersonId(personId);
        person.setLastName(lastName);
        person.setFirstName(firstName);
        person.setAge(age);
        return PersonMapper.mapToPersonDto(personRepository.save(person));
    }

    @Override
    public PersonDto findPersonById(Integer personId) {
        Person person = checkPersonId(personId);
        return PersonMapper.mapToPersonDto(person);
    }

    @Override
    public void deletePerson(Integer personId) {
        checkPersonId(personId);
        personRepository.deleteById(personId);
    }

    private Person checkPersonId(Integer personId) {
        return personRepository.findById(personId).orElseThrow(() -> new DataNotFoundException("Человек по id " +
                personId + " не найдена в базе данных"));
    }
}
