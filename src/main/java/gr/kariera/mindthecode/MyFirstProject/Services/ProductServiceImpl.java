package gr.kariera.mindthecode.MyFirstProject.Services;

import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductWithQuantityExtendedDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }
    @Override
    public Product createOrUpdateProduct(Integer id, Product product) throws Exception {

        if (id != null) {

            if (!id.equals(product.getId())) {

                throw new Exception("id in path does not patch id in body");

            }

        }

        return productRepository.save(product);

    }

    @Override
    public ProductWithQuantityExtendedDto getById(Integer id) throws Exception {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductWithQuantityExtendedDto dto = new ProductWithQuantityExtendedDto();
            dto.setProductId(product.getId());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            return dto;

        }

        else {

            throw new Exception("product not found");

        }

    }

    @Override
    public void deleteProduct(Integer id) {

        Product match = productRepository.findById(id)
                .orElseThrow();
        productRepository.delete(match);

    }

    @Override
    public Page<Product> getProducts(String description, int page, int size, String sort) {

        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("description").ascending() :
                        Sort.by("description").descending());

        Page<Product> res;

        if (description == null) {

            res = productRepository.findAll(paging);

        }

        else {

            res = productRepository.findByDescriptionContainingIgnoreCase(description, paging);

        }

        return res;

    }

}