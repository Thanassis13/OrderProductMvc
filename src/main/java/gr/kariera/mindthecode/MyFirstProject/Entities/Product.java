package gr.kariera.mindthecode.MyFirstProject.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//getter and setter methods,
//generate constructors with no arguments and with all arguments, respectively.
//The uniqueConstraints parameter specifies that the combination of the "name" and "image" columns must be unique in the table.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //the strategy is GenerationType.IDENTITY, which means that the database will automatically assign a unique value to the "product_id" column whenever a new row is inserted.
    @Column(name = "product_id")
    private Integer id;
    private BigDecimal price;
    private String name;
    private String description;
    private int currentQuantity;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    //the two annotations together indicate that the field should be mapped to a binary large object column with a medium size in the database.
    private String image;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private boolean is_deleted;
    private boolean is_activated;

    //    is_deleted: This field might be used to indicate whether a product has been deleted from the system or not.
    //    When a product is deleted, its corresponding is_deleted field is set to true.
    //    This can be useful for soft-deleting records, where they are not actually removed from the database but are hidden from view in the application.

}