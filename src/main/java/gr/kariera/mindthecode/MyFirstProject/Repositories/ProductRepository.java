package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
