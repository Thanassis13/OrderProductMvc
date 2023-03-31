package gr.kariera.mindthecode.MyFirstProject;

import gr.kariera.mindthecode.MyFirstProject.Entities.Admin;
import gr.kariera.mindthecode.MyFirstProject.Entities.Role;
import gr.kariera.mindthecode.MyFirstProject.Repositories.AdminRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class
MyFirstProjectApplication implements CommandLineRunner {

    @Autowired RoleRepository rr;

    @Autowired ProductRepository pr;

    @Autowired AdminRepository ar;

    public static void main(String[] args) {
        SpringApplication.run(MyFirstProjectApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        createDefaultRoles();

        // createDefaultProducts();
    }

    public void createDefaultRoles() {
        Role customer = new Role();
        Role admin = new Role();
        customer.setName("CUSTOMER");
        admin.setName("ADMIN");
        rr.save(customer);
        rr.save(admin);
    }

//	private void createDefaultProducts() {
//
//		Product iphone13 = new Product();
//	}

}