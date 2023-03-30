package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CustomerDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Customer;

public interface CustomerService {

    public abstract Customer register(Integer id, CustomerDto customerDto) throws Exception;

    public abstract Customer update(Integer id, CustomerDto customerDto) throws Exception;

    public abstract Customer login(LoginDto loginDto) throws Exception;

}