package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityExtendedDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Services.OrderService;
import gr.kariera.mindthecode.MyFirstProject.Services.ProductService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {

        this.orderService = orderService;
        this.productService = productService;

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
    public String showCreateForm(@RequestParam(required = false) Integer id, Model model) throws Exception {

        List<ProductWithQuantityExtendedDto> productDtos = productService.getById(id).stream()
                .map(this::toProductWithQuantityExtendedDto)
                .collect(Collectors.toList());

        model.addAttribute("products", productDtos);
        model.addAttribute("order", new NewOrderDto());

        return "create-or-update-order";

    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Order order = orderService.getOrderById(id);

        model.addAttribute("order", order);

        return "create-or-update-order";

    }

    @PostMapping("/create-or-update")
    public String saveCreateForm(@RequestParam Optional<Integer> id, NewOrderDto newOrderDto, Model model) {

        try {

            orderService.createOrUpdateOrder(id.isPresent() ? id.get() : null, newOrderDto);

        }

        catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        Order order = new Order();
        order.setAddress(newOrderDto.getAddress());
        order.setDiscountPercentage(newOrderDto.getDiscountPercentage());
        List<Order.ProductQuantity> productQuantities = new ArrayList<>();

        for (ProductWithQuantityDto productWithQuantityDto : newOrderDto.getProducts()) {
            Product product = productService.getProductById(productWithQuantityDto.getProductId());

            if (product == null) {
                throw new IllegalArgumentException("Product not found: " + productWithQuantityDto.getProductId());

            }
            productQuantities.add(new Order.ProductQuantity(product, productWithQuantityDto.getQuantity()));
        }
        order.setProductQuantities(productQuantities);
        orderService.createOrder(order);

        return "redirect:/orders/index";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {

        orderService.deleteOrder(id);

        return "redirect:/orders/index";

    }

    private ProductWithQuantityExtendedDto toProductWithQuantityExtendedDto(Product product) {

        ProductWithQuantityExtendedDto productDto = new ProductWithQuantityExtendedDto();
        productDto.setProductId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        return productDto;

    }

    private Order toOrder(NewOrderDto orderDto) {

        Order order = new Order();
        order.setAddress(orderDto.getAddress());
        order.setDiscountPercentage(orderDto.getDiscountPercentage());

        List<Order.ProductQuantity> productQuantities = orderDto.getProducts().stream()
                .map(productDto -> new Order.ProductQuantity(productDto.getProductId(), productDto.getQuantity()))
                .collect(Collectors.toList());
        order.setProductQuantities(productQuantities);

        return order;

    }

}