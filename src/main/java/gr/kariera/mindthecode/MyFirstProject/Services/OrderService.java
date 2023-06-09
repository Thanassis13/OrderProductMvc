package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    public abstract Order createOrder(Integer id, NewOrderDto newOrderDto);

    public abstract Order updateOrder(Integer id, Order order) throws Exception;

    public abstract Order getOrderById(Integer id);

    public abstract void deleteOrder(Integer id);

    public abstract Page<Order> getOrders(
            String address,
            int page,
            int size,
            String sort
    );

}