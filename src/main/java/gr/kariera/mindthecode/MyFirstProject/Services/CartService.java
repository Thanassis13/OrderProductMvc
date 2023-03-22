package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CartDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Cart;

public interface CartService {

    public abstract Cart getCart(Integer id);

    public abstract Cart createOrUpdateCart(Integer id, CartDto CartDto) throws Exception;

}
