package gr.kariera.mindthecode.MyFirstProject.MVC;


import gr.kariera.mindthecode.MyFirstProject.Entities.Person;
import gr.kariera.mindthecode.MyFirstProject.Services.PersonService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }
    @GetMapping("/index")
    public String greeting(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            Model model
    ) {
        model.addAttribute("persons", service.getPersons(lastName, page, size, sort));
        model.addAttribute("sort", sort);
        model.addAttribute("lastName", lastName);
        return "persons";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("person",  new Person());
        return "create-or-update-person";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person",  service.getById(id));
        return "create-or-update-person";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        service.deletePerson(id);
        return "redirect:/persons/index";
    }

    @PostMapping("/create-or-update")
    public String saveCreateForm(@RequestParam Optional<Integer> id, @ModelAttribute Person person, Model model) {
        try {
            service.createOrUpdatePerson(id.isPresent() ? id.get() : null, person);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());
        }

        return "redirect:/persons/index";
    }
}