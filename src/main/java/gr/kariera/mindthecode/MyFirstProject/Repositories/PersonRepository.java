package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findByLastName(String lastName, Pageable pageable);
    Page<Person> findByLastNameContaining(String lastName, Pageable pageable);
    Page<Person> findByLastNameIgnoreCase(String lastName, Pageable pageable);

    Page<Person> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
