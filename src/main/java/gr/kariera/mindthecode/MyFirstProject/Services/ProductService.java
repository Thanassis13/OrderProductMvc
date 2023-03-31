package gr.kariera.mindthecode.MyFirstProject.Services;


import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import org.springframework.data.domain.Page;


public interface ProductService {

    public abstract Product createOrUpdateProduct(Integer id, Product product) throws Exception;
    public abstract Product getById(Integer id);
    public abstract void deleteProduct(Integer id);
    public abstract Page<Product> getProducts(
            String description,
            int page,
            int size,
            String sort
    );

}