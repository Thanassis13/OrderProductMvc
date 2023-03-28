package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false")
    List<Product> getAllProducts();

    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true order by rand() asc limit 3 ", nativeQuery = true)
    List<Product> listViewProducts();

    //The query selects all columns (*) from the "products" table, and uses two WHERE clauses to filter the results.
    // The first WHERE clause filters out products that are marked as deleted (is_deleted = false),
    // and the second WHERE clause filters out products that are not activated (is_activated = true).
    // The ORDER BY clause sorts the results in a random order (rand() function), and the LIMIT clause limits the results to three products.

    @Query(value = "select * from products p inner join categories c on c.category_id = p.category_id where p.category_id = ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Integer categoryId);

    // SQL queries are executed against the database
    // JPQL queries are written using the Java Persistence API (JPA), which is a standard API for object-relational mapping in Java.
    // JPQL is object-oriented, which means that it is designed to work with objects and their properties rather than database tables and their columns.


    //When the query is executed, Spring Data JPA replaces the ?1 placeholder with the actual value of the first method parameter (categoryId in this case).
    // If there were additional parameters, they would be numbered accordingly (?2, ?3, etc.).

    @Query(value = "select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1 and p.is_deleted = false and p.is_activated = true")
    List<Product> getProductsInCategory(Integer categoryId);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false" +
            " order by p.price desc")
    List<Product> filterHighPrice();


    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false order by p.price ")
    List<Product> filterLowPrice();

}