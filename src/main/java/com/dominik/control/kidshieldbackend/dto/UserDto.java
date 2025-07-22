package com.dominik.control.kidshieldbackend.dto;

import com.dominik.control.kidshieldbackend.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String email;
    private String password;
    private UserType userType;

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private Boolean isActive;
}
