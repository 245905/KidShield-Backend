package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.*;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.StepCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/stepCount")
@RequiredArgsConstructor
public class StepCountController {

    private final StepCountService stepCountService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<StepCountRequest> stepCountRequests
    ){

        stepCountService.storeData(currentUser.getId(), stepCountRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/time/{time}")
    public ResponseEntity<List<StepCountResponse>> pullRecentData(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long time
    ){

        List<StepCountResponse> stepCountResponses = stepCountService.getRecent(currentUser.getId(), time);

        return ResponseEntity.ok(stepCountResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/start/{start}/end/{end}")
    public ResponseEntity<List<StepCountResponse>> pullDataBetween(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long start,
            @PathVariable Long end
    ){

        List<StepCountResponse> stepCountResponses = stepCountService.getBetween(currentUser.getId(), start, end);

        return ResponseEntity.ok(stepCountResponses);
    }
}
