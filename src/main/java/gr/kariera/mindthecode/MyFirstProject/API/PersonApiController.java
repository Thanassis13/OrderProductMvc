package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.Entities.Person;
import gr.kariera.mindthecode.MyFirstProject.Services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(path = "/api")
public class PersonApiController {

    private final PersonService service;

    public PersonApiController(PersonService service) {
        this.service = service;
    }

    @PutMapping("/persons/{id}")
    public Person update(@PathVariable Integer id, @RequestBody Person person) {
        try {
            return service.createOrUpdatePerson(id, person);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }

    @PostMapping("/persons")
    public Person newPerson(@RequestBody Person person) {
        try {
            return service.createOrUpdatePerson(person.getId(), person);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }

    @GetMapping("/persons/{id}")
    public Person one(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/persons")
    public Page<Person> all(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort
    ) {
        return service.getPersons(lastName, page, size, sort);
    }

    @DeleteMapping("/persons/{id}")
    public void delete(@PathVariable Integer id) {
        service.deletePerson(id);
    }
}
