package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.Services.CustomerService;
import gr.kariera.mindthecode.MyFirstProject.Services.ProductService;
import gr.kariera.mindthecode.MyFirstProject.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ShoppingCartController {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(CustomerService customerService, ProductService productService, ShoppingCartService shoppingCartService) {

        this.customerService = customerService;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;

    }

}