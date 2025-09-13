package com.works.entities.dto;

import com.works.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserLoginDto implements Serializable {
    @NotNull
    @Email
    @NotEmpty
    String email;
    @NotNull
    @Size(min = 5)
    @NotEmpty
    String password;
}