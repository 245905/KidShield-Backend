package com.dominik.control.kidshieldbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email couldn't be blank")
    @Email(message = "Should be a valid email")
    private String email;

    @NotBlank(message = "Email couldn't be blank")
    private String password;
}
