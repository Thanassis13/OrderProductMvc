package gr.kariera.mindthecode.MyFirstProject.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// used lobmok to generate getters setters and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName", "email", "userName", "password"}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

}