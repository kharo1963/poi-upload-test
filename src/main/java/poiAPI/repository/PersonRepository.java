package poiAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import poiAPI.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByLastNameInOrderByFirstNameDesc(List<String> lastNames);
}