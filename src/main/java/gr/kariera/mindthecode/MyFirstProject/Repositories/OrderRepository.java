package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByAddressContainingIgnoreCase(String address, Pageable pageable);
}
