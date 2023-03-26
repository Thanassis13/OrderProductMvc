package gr.kariera.mindthecode.MyFirstProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// used lombok to generate getters setters and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;
}
