package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Services.OrderService;
import gr.kariera.mindthecode.MyFirstProject.Services.ProductService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;


    public OrderController(OrderService orderService, ProductService productService) {

        this.orderService = orderService;

        this.productService = productService;

    }

    @GetMapping("/create")
    public String all(

            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort, Model model

    ) {

        model.addAttribute("newOrderDto", new NewOrderDto());
        model.addAttribute("products", productService.getProducts(description, page, size, sort));
        model.addAttribute("sort", sort);
        model.addAttribute("description", description);

        return "create-or-update-order";

    }

    @GetMapping("/all")
    public String showCreateForm(@RequestParam(required = false) String address,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 @RequestParam(defaultValue = "ASC", required = false) String sort,Model model) {

        model.addAttribute("orders", orderService.getOrders(address, page, size, sort));
        model.addAttribute("sort", sort);
        model.addAttribute("address", address);

        return "orders";

    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("order",  orderService.getOrderById(id));

        return "create-or-update-order";

    }

    @PostMapping("/create-or-update")
    public String saveCreateForm(NewOrderDto newOrderDto) {

        try {

            orderService.createOrder(newOrderDto);

        }

        catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        return "redirect:/orders/all";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {

        orderService.deleteOrder(id);

        return "redirect:/orders/index";

    }

    }