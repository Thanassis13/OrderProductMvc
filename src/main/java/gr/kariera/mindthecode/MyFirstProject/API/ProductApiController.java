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

    private final ProductRepository repo;

    public ProductApiController(ProductRepository repo) {
        this.repo = repo;
    }

    @PutMapping("/products/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        if (!id.equals(product.getId())) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "id in path does not patch id in body");
        }
        return repo.save(product);
    }

    @PostMapping("/products")
    public Product newPerson(@RequestBody Product product) {
        return repo.save(product);
    }

    @GetMapping("/products/{id}")
    public Product one(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow();
    }

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
            res = repo.findAll(paging);
        } else {
            res = repo.findByDescriptionContainingIgnoreCase(description, paging);
        }

        return res;
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Integer id) {
        Product match = repo.findById(id)
                .orElseThrow();
        repo.delete(match);
    }
}
