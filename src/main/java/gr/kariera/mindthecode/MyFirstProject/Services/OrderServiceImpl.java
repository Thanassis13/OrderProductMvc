package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProductPK;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.OrderRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;

    }
    @Override
    public Order getOrderById(Integer id) {

        return orderRepository.findById(id)
                .orElseThrow();

    }
    @Override
    public Page<Order> getOrders(String address, int page, int size, String sort) {

        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("address").ascending() :
                        Sort.by("address").descending());

        Page<Order> res;

        if (address == null) {

            res = orderRepository.findAll(paging);

        }

        else {

            res = orderRepository.findByAddressContainingIgnoreCase(address, paging);

        }

        return res;

    }

    public Order createOrUpdateOrder(Integer id, NewOrderDto newOrderDto) throws Exception {

        if (id != null) {

            if(!id.equals(newOrderDto.getAddress())) {

                throw new Exception("id in path does not patch id in body");

            }

        }

            Order order = new Order();
            order.setAddress(newOrderDto.getAddress());
            order.setDiscountPercentage(newOrderDto.getDiscountPercentage());
            List<OrderProduct> orderProducts = new ArrayList<>();
            BigDecimal total = new BigDecimal(0);
            for (ProductWithQuantityDto productWithQuantityDto : newOrderDto.getProducts()) {
                Product product = productService.getProductById(productWithQuantityDto.getProductId());
                if (product == null) {

                    throw new Exception("Product with id " + productWithQuantityDto.getProductId() + " not found.");

                }

                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProduct(product);
                orderProduct.setQuantity(productWithQuantityDto.getQuantity());
                orderProduct.setOrder(order);
                orderProducts.add(orderProduct);
                total = total.add(product.getPrice().multiply(new BigDecimal(productWithQuantityDto.getQuantity())));

            }

            BigDecimal discountAmount = total.multiply(new BigDecimal(order.getDiscountPercentage() / 100));
            order.setTotal(total.subtract(discountAmount));
            order.setOrderProducts(orderProducts);

            return orderRepository.save(order);

    }

//            Order order = new Order();
//            order.setAddress(newOrder.getAddress());
//            order.setDiscountPercentage(newOrder.getDiscountPercentage());
//            order = orderRepository.save(order);
//
//            final Order finalOrder = order;
//            newOrder.getProducts()
//                    .stream()
//                    .forEach(nop -> {
//
//                        Product p = productRepository
//                                .findById(nop.getProductId())
//                                .orElseThrow();
//                        OrderProduct op = new OrderProduct();
//                        OrderProductPK opPK = new OrderProductPK();
//                        opPK.setOrderId(finalOrder.getId());
//                        opPK.setProductId(p.getId());
//                        op.setId(opPK);
//                        op.setOrder(finalOrder);
//                        op.setProduct(p);
//                        op.setQuantity(nop.getQuantity());
//                        finalOrder.getOrderProducts().add(op);
//                        finalOrder.setOrderProducts(finalOrder.getOrderProducts());
//
//                    });
//
//            Order result = orderRepository.save(finalOrder);
//            return orderRepository.findById(result.getId())
//                    .orElseThrow();

    public void deleteOrder(Integer id) {

        Order match = orderRepository.findById(id)
                .orElseThrow();
        orderRepository.delete(match);

    }

}