package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {



}
