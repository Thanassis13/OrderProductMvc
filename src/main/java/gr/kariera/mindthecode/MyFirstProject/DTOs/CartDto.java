package gr.kariera.mindthecode.MyFirstProject.DTOs;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Collection;


public class CartDto implements Serializable {

    private Collection<ProductWithQuantityDto> cartItems;
    private Integer CartDtoId;
    
    public CartDto() {}

    public Integer getCartDtoId() {

        return CartDtoId;

    }

    public CartDto(Collection<ProductWithQuantityDto> products) {

        this.cartItems = products;

    }

    public Collection<ProductWithQuantityDto> getProducts() {

        return cartItems;

    }

    public void setProducts(Collection<ProductWithQuantityDto> products) {

        this.cartItems = products;

    }

    public void addItem(ProductWithQuantityDto product) {

        cartItems.add(product);

    }

}
