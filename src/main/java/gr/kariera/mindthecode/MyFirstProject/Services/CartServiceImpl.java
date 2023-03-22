package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CartDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Cart;
import gr.kariera.mindthecode.MyFirstProject.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {

        this.cartRepository = cartRepository;

    }

    @Override
    public Cart getCart(Integer id) {

        return cartRepository.findById(id).orElseThrow();

    }

    @Override
    public Cart createOrUpdateCart(Integer id, CartDto cartDto) throws Exception {

        if (id != null) {

            if (!id.equals(cartDto.getCartDtoId())) {

                throw new Exception("id in path does not patch id in body");

            }

        }

        return cartRepository.save(getCart(id));

    }

}