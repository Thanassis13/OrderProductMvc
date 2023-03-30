package gr.kariera.mindthecode.MyFirstProject.Repositories;

import gr.kariera.mindthecode.MyFirstProject.DTOs.CategoryDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.is_activated = true and c.is_deleted = false")
    List<Category> findAllByActivated();


    /*Customer*/
    @Query("select new gr.kariera.mindthecode.MyFirstProject.DTOs.CategoryDto(c.id, c.name, count(p.category.id)) from Category c inner join Product p on p.category.id = c.id " +
            " where c.is_activated = true and c.is_deleted = false group by c.id")
    List<CategoryDto> getCategoryAndProduct();

}