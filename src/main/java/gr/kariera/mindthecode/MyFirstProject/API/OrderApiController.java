package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProductPK;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.OrderRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(path = "/api")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderApiController(OrderRepository orderRepository, ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/orders/{id}")
    public Order update(@PathVariable Integer id, @RequestBody Order order) {

        if (!id.equals(order.getId())) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "id in path does not patch id in body");

        }

        return orderRepository.save(order);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/orders")
    @Transactional
    public Order newOrder(@RequestBody NewOrderDto newOrder) {

        Order order = new Order();
        order.setAddress(newOrder.getAddress());
        // order.setDiscountPercentage(newOrder.getDiscountPercentage());
        order = orderRepository.save(order);

        final Order finalOrder = order;
        newOrder.getProducts()
                .stream()
                .forEach(nop -> {

                    Product p = productRepository
                            .findById(nop.getProductId())
                            .orElseThrow();
                    OrderProduct op = new OrderProduct();
                    OrderProductPK opPK = new OrderProductPK();
                    opPK.setOrderId(finalOrder.getId());
                    opPK.setProductId(p.getId());
                    op.setId(opPK);
                    op.setOrder(finalOrder);
                    op.setProduct(p);
                    op.setQuantity(nop.getQuantity());
                    finalOrder.getOrderProducts().add(op);
                    finalOrder.setOrderProducts(finalOrder.getOrderProducts());

                });

        Order result = orderRepository.save(finalOrder);
        return orderRepository.findById(result.getId())
                .orElseThrow();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/orders/{id}")
    public Order orders(@PathVariable Integer id) {

        return orderRepository.findById(id)
                .orElseThrow();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/orders")
    public Page<Order> all(

            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort

    ) {

        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("address").ascending() :
                        Sort.by("address").descending());

        Page<Order> res;

        if (address == null) {

            res = orderRepository.findAll(paging);

        } else {
            res = orderRepository.findByAddressContainingIgnoreCase(address, paging);

        }

        return res;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable Integer id) {

        Order match = orderRepository.findById(id)
                .orElseThrow();
        orderRepository.delete(match);

    }

}