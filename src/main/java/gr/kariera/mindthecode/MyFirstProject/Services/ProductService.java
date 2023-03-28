package gr.kariera.mindthecode.MyFirstProject.Services;


import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProductService {

    /*Admin*/

    public abstract List<ProductDto> findAll();

    public abstract Product save(Integer id, ProductDto productDto) throws Exception;

    public abstract Product update(Integer id, ProductDto productDto) throws Exception;

    public abstract void deleteProduct(Integer id);

    public abstract void enableById(Integer id);

    public abstract ProductDto getById(Integer id);

    public abstract Page<Product> getProducts(
            String description,
            int page,
            int size,
            String sort
    );

    /*Customer*/

    List<Product> getAllProducts();

    public abstract Product getProductById(Integer id);

    List<Product> listViewProducts();

    List<Product> getRelatedProducts(Long categoryId);

    List<Product> getProductsInCategory(Long categoryId);

    List<Product> filterHighPrice();

    List<Product> filterLowPrice();

}