package com.works.entities.dto;

import com.works.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.works.entities.User}
 */
@Value
public class UserRegisterDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    @NotBlank
    String firstName;
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String lastName;
    @NotNull
    @Size
    @Email
    @NotEmpty
    String email;
    @NotNull
    @Size(min = 5, max = 10)
    @NotEmpty
    String password;
    Boolean enabled = true;

    private List<Role> roles;
}