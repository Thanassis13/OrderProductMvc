package gr.kariera.mindthecode.MyFirstProject.Repositories;


import gr.kariera.mindthecode.MyFirstProject.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByUsername(String username);

}
