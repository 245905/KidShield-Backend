package com.dominik.control.kidshieldbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointResponse {

    private Double lat;
    private Double lon;
    private Float speed;
    private Long fixTime;
    private Long receivedTime;
}
