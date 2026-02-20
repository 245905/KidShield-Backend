package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.MessageResponse;
import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.dto.UsageStatsResponse;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.UsageStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/usageStats")
@RequiredArgsConstructor
public class UsageStatsController {

    private final UsageStatsService usageStatsService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<UsageStatsRequest> usageStatsRequests
    ){

        usageStatsService.storeData(currentUser.getId(), usageStatsRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/date/{date}")
    public ResponseEntity<List<UsageStatsResponse>> pullDataByDate(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Date date
            ){

        List<UsageStatsResponse> usageStatsResponses = usageStatsService.getByDate(currentUser.getId(), date);

        return ResponseEntity.ok(usageStatsResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/date/{date}/name/{name}")
    public ResponseEntity<List<UsageStatsResponse>> pullDataByDateAndName(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Date date,
            @PathVariable String name
    ){

        List<UsageStatsResponse> usageStatsResponses = usageStatsService.getByDateAndName(currentUser.getId(), date, name);

        return ResponseEntity.ok(usageStatsResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UsageStatsResponse>> pullDataByName(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable String name
    ){

        List<UsageStatsResponse> usageStatsResponses = usageStatsService.getByName(currentUser.getId(), name);

        return ResponseEntity.ok(usageStatsResponses);
    }
}
