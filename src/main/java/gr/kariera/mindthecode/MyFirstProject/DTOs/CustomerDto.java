package gr.kariera.mindthecode.MyFirstProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// used lombok to generate getters setters and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    //should not be null or empty
    //should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String lastName;

    // email should be a valid email format
    // email should not be null or empty
    @NotEmpty
    @Email
    private String email;

    //should not be null or empty
    //should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "username should have at least 2 characters")
    private String username;

    // password should not be null or empty
    // password should have at least 8 characters
    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

}