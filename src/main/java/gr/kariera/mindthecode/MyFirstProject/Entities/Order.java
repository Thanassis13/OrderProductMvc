package gr.kariera.mindthecode.MyFirstProject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityDto;
import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityExtendedDto;
import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    public Order() {
        this.orderProducts = new ArrayList<>();
    }

    public Order(Collection<OrderProduct> products) {
        this.orderProducts = products;
    }

    public Order(String address) {
        this.address = address;
        this.orderProducts = new ArrayList<>();
    }

    public Order(Collection<OrderProduct> products, String address) {
        this.orderProducts = products;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Collection<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Collection<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Collection<OrderProduct> orderProducts = new ArrayList<>();

    @Transient
    private Collection<ProductWithQuantityDto> products;

    public Collection<ProductWithQuantityDto>  getProducts() {
        return orderProducts
                .stream()
                .map(op -> {
                    ProductWithQuantityExtendedDto pwq = new ProductWithQuantityExtendedDto();
                    pwq.setProductId(op.getProduct().getId());
                    pwq.setQuantity(op.getQuantity());
                    pwq.setDescription(op.getProduct().getDescription());
                    pwq.setPrice(op.getProduct().getPrice());
                    return pwq;
                })
                .collect(Collectors.toList());
    }

    @Transient
    private BigDecimal totalCost;

    public BigDecimal getTotalCost() {
        BigDecimal total = orderProducts
                .stream()
                .map(op -> {
                    return op.getProduct().getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            op.getQuantity()
                                    )
                            );
                })
                .reduce((acc, cur) -> acc.add(cur))
                .orElseThrow();

        return total.multiply(BigDecimal.valueOf(1-discountPercentage));
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    private Double discountPercentage = 0d;

    private String address;
}
