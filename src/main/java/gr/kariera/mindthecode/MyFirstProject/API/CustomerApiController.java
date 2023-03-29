package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CustomerDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Customer;
import gr.kariera.mindthecode.MyFirstProject.Repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class CustomerApiController {


    private final CustomerRepository customerRepository;

    public CustomerApiController(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/customers")
    public Customer register(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {

        Customer customer = new Customer();

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());

        Customer customerSave = customerRepository.save(customer);

        return customerRepository.findById(customerSave.getId()).orElseThrow();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/customer")
    public Customer login(@RequestBody LoginDto loginDto) throws Exception {

        Customer customer = customerRepository.findByUsername(loginDto.getUsername());

        if (customer == null) {

            throw new Exception("Unable to find customer with username" + loginDto.getUsername());

        }

        if (customer.getPassword().equals(loginDto.getPassword())) {

            return customer;

        } else {

            throw new Exception("Wrong password");

        }

    }

}