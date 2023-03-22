package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CartDto;
import gr.kariera.mindthecode.MyFirstProject.Services.CartService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {

        this.cartService = cartService;}


        @GetMapping("/cart")
    public String showCart(Integer id, Model model) {

        model.addAttribute("cart", cartService.getCart(id));

        return "create-or-update-order";

    }

    @GetMapping("/createCart")
    public String createCartForm(Model model) {

        model.addAttribute("cart",  new CartDto());

        return "create-or-update-cart";

    }

    @PostMapping("/create-or-update")
    public String saveCreateForm(@RequestParam Optional<Integer> id, @ModelAttribute CartDto cartDto, Model model) {

        try {

            cartService.createOrUpdateCart(id.isPresent() ? id.get() : null, cartDto);

        }

        catch (Exception e) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

        }

        return "redirect:/products/index";

    }



}
