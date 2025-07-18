package com.dominik.control.kidshieldbackend.dto;

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

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private Boolean isActive;
}
