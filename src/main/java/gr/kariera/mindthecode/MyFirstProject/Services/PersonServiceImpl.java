package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.Entities.Person;
import gr.kariera.mindthecode.MyFirstProject.Repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repo;

    public PersonServiceImpl(PersonRepository repo) {
        this.repo = repo;
    }
    @Override
    public Person createOrUpdatePerson(Integer id, Person person) throws Exception {
        if (id != null) {
            if (!id.equals(person.getId())) {
                throw new Exception("id in path does not patch id in body");
            }
        }
        return repo.save(person);
    }

    @Override
    public void deletePerson(Integer id) {
        Person match = repo.findById(id)
                .orElseThrow();
        repo.delete(match);
    }

    @Override
    public Person getById(Integer id) {
        return repo.findById(id)
                .orElseThrow();
    }

    @Override
    public Page<Person> getPersons(String lastName, int page, int size, String sort) {
        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("lastName").ascending() :
                        Sort.by("lastName").descending());

        Page<Person> res;
        if (lastName == null) {
            res = repo.findAll(paging);
        } else {
            res = repo.findByLastNameContainingIgnoreCase(lastName, paging);
        }

        return res;
    }


}
