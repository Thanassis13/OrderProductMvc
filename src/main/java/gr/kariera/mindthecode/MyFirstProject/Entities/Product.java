package gr.kariera.mindthecode.MyFirstProject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    public Collection<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Collection<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Collection<OrderProduct> orderProducts = new ArrayList<>();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private BigDecimal price;

    private String description;

}
