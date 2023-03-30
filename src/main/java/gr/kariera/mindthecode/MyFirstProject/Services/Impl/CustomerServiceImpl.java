package gr.kariera.mindthecode.MyFirstProject.Services.Impl;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CustomerDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Customer;
import gr.kariera.mindthecode.MyFirstProject.Repositories.CustomerRepository;
import gr.kariera.mindthecode.MyFirstProject.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepo) {

        this.customerRepository = customerRepo;

    }

    @Override
    public Customer register(Integer id, CustomerDto customerDto) throws Exception {

        Customer customer = new Customer();

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());

        Customer customerSave = customerRepository.save(customer);

        return customerRepository.findById(customerSave.getId()).orElseThrow();
    }

    @Override
    public Customer update(Integer id, CustomerDto customerDto) throws Exception {
        return null;
    }

    @Override
    public Customer login(LoginDto loginDto) throws Exception {

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