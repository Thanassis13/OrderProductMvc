package gr.kariera.mindthecode.MyFirstProject;

import gr.kariera.mindthecode.MyFirstProject.Entities.Role;
import gr.kariera.mindthecode.MyFirstProject.Repositories.AdminRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		// createDefaultAdmin();
		// createDefaultProducts();
	}

//	private void createDefaultAdmin() {
//		Admin admin = new Admin();
//		admin.setUsername("root");
//		admin.setPassword("mindthecode");
//		admin.setEmail("mindthecode@gmail.gr");
//		admin.setRole(rr.findByName("ADMIN"));
//		ar.save(admin);
//
//	}

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