package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Services.OrderService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {

        this.service = service;

    }

    @GetMapping("/index")
    public String all(

            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort, Model model

    ) {

        return "orders";

    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("order",  new Order());

        return "create-or-update-order";

    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("order",  service.getOrderById(id));

        return "create-or-update-order";

    }

    @PostMapping("/create-or-update")
    public String saveCreateForm(@RequestParam Optional<Integer> id, Order order, Model model) {

        try {

            service.createOrUpdateOrder(id.isPresent() ? id.get() : null, order);

        }

        catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        return "redirect:/orders/index";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {

        service.deleteOrder(id);

        return "redirect:/orders/index";

    }

}