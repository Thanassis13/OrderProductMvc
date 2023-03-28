package gr.kariera.mindthecode.MyFirstProject.Services.Impl;

import gr.kariera.mindthecode.MyFirstProject.DTOs.ProductDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import gr.kariera.mindthecode.MyFirstProject.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }

    /*Admin*/

    @Override
    public List<ProductDto> findAll() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;

    }

    @Override
    public Product save(Integer id, ProductDto productDto) throws Exception {

        Product product = new Product();

        if (id != null) {

            if (!id.equals(product.getId())) {

                throw new Exception("id in path does not patch id in body");

            }

        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.set_activated(true);
        product.set_deleted(false);
        return productRepository.save(product);

    }

    @Override
    public Product update(Integer id, ProductDto productDto) throws Exception {

        Product product = productRepository.getById(productDto.getId());

        if (id != null) {

            if (!id.equals(product.getId())) {

                throw new Exception("id in path does not patch id in body");

            }

        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.set_activated(true);
        product.set_deleted(false);
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(Integer id) {

        Product match = productRepository.findById(id)
                .orElseThrow();
        productRepository.delete(match);

    }

    @Override
    public void enableById(Integer id) {
        Product product = productRepository.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Integer id) {

        Product product = productRepository.getById(id);

        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());

        return productDto;

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

        } else {

            res = productRepository.findByDescriptionContainingIgnoreCase(description, paging);

        }

        return res;

    }

    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : products) {

            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());
            productDtoList.add(productDto);

        }

        return productDtoList;

    }

    /*Customer*/

    @Override
    public List<Product> getAllProducts() {

        return productRepository.getAllProducts();

    }

    @Override
    public Product getProductById(Integer id) {

        return productRepository.findById(id)
                .orElseThrow();

    }

    @Override
    public List<Product> listViewProducts() {

        return productRepository.listViewProducts();

    }

    @Override
    public List<Product> getRelatedProducts(Integer categoryId) {

        return productRepository.getRelatedProducts(categoryId);

    }

    @Override
    public List<Product> getProductsInCategory(Integer categoryId) {

        return productRepository.getProductsInCategory(categoryId);

    }

    @Override
    public List<Product> filterHighPrice() {

        return productRepository.filterHighPrice();

    }

    @Override
    public List<Product> filterLowPrice() {

        return productRepository.filterLowPrice();

    }

}