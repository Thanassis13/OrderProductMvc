package gr.kariera.mindthecode.MyFirstProject.MVC;

import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Services.ProductService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

        private final ProductService service;

        public ProductController(ProductService service) {

            this.service = service;

        }
        @GetMapping("/index")
        public String greeting(

                @RequestParam(required = false) String description,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "3") int size,
                @RequestParam(defaultValue = "ASC", required = false) String sort,
                Model model
        ) {

            model.addAttribute("persons", service.getProducts(description, page, size, sort));
            model.addAttribute("sort", sort);
            model.addAttribute("lastName", description);
            return "products";

        }

        @GetMapping("/create")
        public String showCreateForm(Model model) {

            model.addAttribute("product",  new Product());

            return "create-or-update-product";

        }
        @GetMapping("/edit/{id}")
        public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

            model.addAttribute("product",  service.getById(id));

            return "create-or-update-product";

        }

        @PostMapping("/delete/{id}")
        public String delete(@PathVariable("id") Integer id, Model model) {

            service.deleteProduct(id);

            return "redirect:/persons/index";

        }

        @PostMapping("/create-or-update")
        public String saveCreateForm(@RequestParam Optional<Integer> id, @ModelAttribute Product product, Model model) {

            try {

                service.createOrUpdateProduct(id.isPresent() ? id.get() : null, product);

            }

            catch (Exception e) {

                throw new HttpClientErrorException(HttpStatusCode.valueOf(400), e.getMessage());

            }

            return "redirect:/products/index";

        }

    }