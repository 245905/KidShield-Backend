package com.dominik.control.kidshieldbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PairByPinRequest {

    @NotNull(message = "Pairing code should be present")
    @Size(min = 6, max = 6, message = "Pin is 6 digits long")
    private String pin;
}
