package gr.kariera.mindthecode.MyFirstProject.DTOs;

import gr.kariera.mindthecode.MyFirstProject.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private int currentQuantity;
    private Category category;
    private String image;
    private boolean activated;
    private boolean deleted;

}