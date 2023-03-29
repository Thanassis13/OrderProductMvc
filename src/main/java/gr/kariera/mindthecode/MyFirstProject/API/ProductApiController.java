package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(path = "/api")
public class ProductApiController {

    private final ProductRepository productRepository;

    public ProductApiController(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/products/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {

        if (!id.equals(product.getId())) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "id in path does not patch id in body");

        }

        return productRepository.save(product);

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/products")
    public Product newPerson(@RequestBody Product product) {

        return productRepository.save(product);

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/products/{id}")
    public Product products(@PathVariable Integer id) {

        return productRepository.findById(id)
                .orElseThrow();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/products")
    public Page<Product> all(

            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort

    ) {

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
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Integer id) {

        Product match = productRepository.findById(id)
                .orElseThrow();
        productRepository.delete(match);

    }

}