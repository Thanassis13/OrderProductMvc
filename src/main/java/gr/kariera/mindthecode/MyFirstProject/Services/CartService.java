package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CartDto;

public interface CartService {

    public abstract CartDto getCart(Integer id);

    public abstract CartDto createOrUpdateCart(Integer id, CartDto CartDto) throws Exception;

}
