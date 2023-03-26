package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CustomerDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;

    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("customerDto", new CustomerDto());

        return "create-user-form";

    }

    @PostMapping("/register")
    public String register(Integer id, @ModelAttribute CustomerDto customerDto) throws Exception {

        try {

            customerService.register(id, customerDto);

        } catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        return "redirect:createlogin";

    }


    @GetMapping("/createlogin")
    public String loginForm(Model model) {

        model.addAttribute("loginDto", new LoginDto());

        return "create-login-form";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto) throws Exception {

        try {

            customerService.login(loginDto);

        } catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        return "loggedIn";

    }

}