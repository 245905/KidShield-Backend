package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.dto.HourlyStatsResponse;
import com.dominik.control.kidshieldbackend.dto.MessageResponse;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.HourlyStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hourlyStats")
@RequiredArgsConstructor
public class HourlyStatsController {

    private final HourlyStatsService hourlyStatsService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<HourlyStatsRequest> hourlyStatsRequests
    ){

        hourlyStatsService.storeData(currentUser.getId(), hourlyStatsRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/date/{date}")
    public ResponseEntity<List<HourlyStatsResponse>> pullDataByDate(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Date date
    ){

        List<HourlyStatsResponse> hourlyStatsResponses = hourlyStatsService.getByDate(currentUser.getId(), date);

        return ResponseEntity.ok(hourlyStatsResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/date/{date}/name/{name}")
    public ResponseEntity<List<HourlyStatsResponse>> pullDataByDateAndName(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Date date,
            @PathVariable String name
    ){

        List<HourlyStatsResponse> hourlyStatsResponses = hourlyStatsService.getByDateAndName(currentUser.getId(), date, name);

        return ResponseEntity.ok(hourlyStatsResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<HourlyStatsResponse>> pullDataByName(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable String name
    ){

        List<HourlyStatsResponse> hourlyStatsResponses = hourlyStatsService.getByName(currentUser.getId(), name);

        return ResponseEntity.ok(hourlyStatsResponses);
    }
}
