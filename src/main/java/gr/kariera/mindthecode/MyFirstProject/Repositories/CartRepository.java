package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findById(Integer id);

}
