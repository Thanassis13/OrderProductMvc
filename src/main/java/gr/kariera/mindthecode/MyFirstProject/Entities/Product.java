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
    private BigDecimal price;
    private String name;
    private String description;
    private String pictureUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Collection<OrderProduct> orderProducts = new ArrayList<>();

    public Product() {}

    public Product(Integer id, String description, BigDecimal price, String pictureUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;

    }

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

    public Collection<OrderProduct> getOrderProducts() {

        return orderProducts;

    }

    public void setOrderProducts(Collection<OrderProduct> orderProducts) {

        this.orderProducts = orderProducts;

    }

    public String getPictureUrl() {

        return pictureUrl;

    }

    public void setPictureUrl(String pictureUrl) {

        this.pictureUrl = pictureUrl;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

}