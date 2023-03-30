package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.DTOs.AdminDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Admin;
import gr.kariera.mindthecode.MyFirstProject.Repositories.AdminRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminApiController {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public AdminApiController(AdminRepository adminRepository, RoleRepository rolerepository) {

        this.adminRepository = adminRepository;
        this.roleRepository = rolerepository;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public Admin register(@RequestBody AdminDto adminDto) {

        Admin admin = new Admin();

        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));

        Admin adminSave = adminRepository.save(admin);

        return adminRepository.findById(adminSave.getId()).orElseThrow();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public Admin login(@RequestBody LoginDto loginDto) throws Exception {

        Admin admin = adminRepository.findByUsername(loginDto.getUsername());

        if (admin == null) {

            throw new Exception("Unable to find customer with username" + loginDto.getUsername());

        }

        if (admin.getPassword().equals(loginDto.getPassword())) {

            return admin;

        } else {

            throw new Exception("Wrong password");

        }

    }

}