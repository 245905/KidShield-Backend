package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.UsageStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/usageStats")
@RequiredArgsConstructor
public class UsageStatsController {

    private final UsageStatsService usageStatsService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<?> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<UsageStatsRequest> usageStatsRequests
    ){

        usageStatsService.storeData(currentUser.getId(), usageStatsRequests);
        return ResponseEntity.ok("");
    }
}
