package gr.kariera.mindthecode.MyFirstProject.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NewOrderDto implements Serializable {
    private String address;

    private Double discountPercentage = 0.1;

    private Collection<ProductWithQuantityDto> products;

    public NewOrderDto() {

        this.products = new ArrayList<>();

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }

    public Double getDiscountPercentage() {

        return discountPercentage;

    }

    public void setDiscountPercentage(Double discountPercentage) {

        this.discountPercentage = discountPercentage;

    }

    public Collection<ProductWithQuantityDto> getProducts() {

        return products;

    }

    public void setProducts(Collection<ProductWithQuantityDto> products) {

        this.products = products;

    }

}