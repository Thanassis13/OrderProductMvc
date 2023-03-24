package gr.kariera.mindthecode.MyFirstProject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cart {

    @Id
    @GeneratedValue
    private Integer cartId;

    public Integer getCartId() {

        return cartId;

    }

}