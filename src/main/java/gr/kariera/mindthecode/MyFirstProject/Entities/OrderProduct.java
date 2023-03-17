package gr.kariera.mindthecode.MyFirstProject.Entities;

import jakarta.persistence.*;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "Order_Product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductPK id;

    @ManyToOne
    @Lazy(false)
    @MapsId("order_id")
    private Order order;

    @ManyToOne
    @Lazy(false)
    @MapsId("product_id")
    private Product product;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Integer quantity;
    public OrderProduct() {}

    public OrderProductPK getId() {
        return id;
    }

    public void setId(OrderProductPK id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
