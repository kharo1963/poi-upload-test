package poiAPI.mapper;

import lombok.experimental.UtilityClass;
import poiAPI.dto.PersonDto;
import poiAPI.model.Person;

@UtilityClass
public class PersonMapper {

    public Person mapToPerson(PersonDto personDto) {
        return Person.builder()
                .id(personDto.getId())
                .lastName(personDto.getLastName())
                .firstName(personDto.getFirstName())
                .age(personDto.getAge())
                .build();
    }

    public PersonDto mapToPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .lastName(person.getLastName())
                .firstName(person.getFirstName())
                .age(person.getAge())
                .build();
    }
}
