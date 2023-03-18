package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Entities.Person;
import gr.kariera.mindthecode.MyFirstProject.Repositories.OrderRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;

    }

    public Order getOrderById(Integer id) {

        return orderRepository.findById(id)
                .orElseThrow();

    }

    public Page<Order> getOrders(String address, int page, int size, String sort) {

        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("lastName").ascending() :
                        Sort.by("lastName").descending());

        Page<Order> res;

        if (address == null) {

            res = orderRepository.findAll(paging);

        }

        else {

            res = orderRepository.findByAddressContainingIgnoreCase(address, paging);

        }

        return res;

    }

    public void deleteOrder(Integer id) {

        Order match = orderRepository.findById(id)
                .orElseThrow();
        orderRepository.delete(match);

    }

}