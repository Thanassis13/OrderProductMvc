package gr.kariera.mindthecode.MyFirstProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {

    private Long categoryId;
    private String categoryName;
    private Long numberOfProduct;

}