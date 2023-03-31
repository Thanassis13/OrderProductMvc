package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CustomerDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.LoginDto;
import gr.kariera.mindthecode.MyFirstProject.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

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

    //@Valid, after http request and before creating a customer it will check if it is valid
    @PostMapping("/register")
    public String register(@Valid Integer id, @ModelAttribute CustomerDto customerDto, BindingResult bindingResult) /*throws Exception*/ {
        if (bindingResult.hasErrors()) {
            return "create-user-form";
        }

//        try {
//
//            customerService.register(id, customerDto);
//
//        } catch (Exception e) {
//
//            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());
//
//        }

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