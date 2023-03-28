package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.Entities.Customer;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Entities.ShoppingCart;

public interface ShoppingCartService {

        public abstract ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

        public abstract ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);

        public abstract ShoppingCart deleteItemFromCart(Product product, Customer customer);

}
