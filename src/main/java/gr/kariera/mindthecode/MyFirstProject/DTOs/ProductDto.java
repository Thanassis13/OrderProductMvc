package gr.kariera.mindthecode.MyFirstProject.DTOs;

import gr.kariera.mindthecode.MyFirstProject.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    @Size(min = 2, max = 50, message = "must be between 2 and 50 characters")
    private String name;
    @Size(min = 2, max = 100, message = "must be between 2 and 100 characters")
    private String description;
    @NotNull
    //The value of the field must be a decimal value, greater than or equal to the number in the value element.
    @DecimalMin("0.00")
    private BigDecimal price;
    @NotNull
    private int currentQuantity;
    @NotNull
    private Category category;
    private String image;
    private boolean activated;
    private boolean deleted;

}