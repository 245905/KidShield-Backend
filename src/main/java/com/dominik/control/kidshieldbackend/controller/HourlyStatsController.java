package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.HourlyStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hourlyStats")
@RequiredArgsConstructor
public class HourlyStatsController {

    private final HourlyStatsService hourlyStatsService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<?> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<HourlyStatsRequest> hourlyStatsRequests
    ){

        hourlyStatsService.storeData(currentUser.getId(), hourlyStatsRequests);
        return ResponseEntity.ok("Successfully stored data");
    }
}
