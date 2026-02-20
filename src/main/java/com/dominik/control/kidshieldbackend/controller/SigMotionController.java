package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.*;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.SigMotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/sigMotion")
@RequiredArgsConstructor
public class SigMotionController {

    private final SigMotionService sigMotionService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<SigMotionRequest> sigMotionRequests
    ){

        sigMotionService.storeData(currentUser.getId(), sigMotionRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/time/{time}")
    public ResponseEntity<List<SigMotionResponse>> pullRecentData(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long time
    ){

        List<SigMotionResponse> sigMotionResponses = sigMotionService.getRecent(currentUser.getId(), time);

        return ResponseEntity.ok(sigMotionResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/start/{start}/end/{end}")
    public ResponseEntity<List<SigMotionResponse>> pullDataBetween(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long start,
            @PathVariable Long end
    ){

        List<SigMotionResponse> sigMotionResponses = sigMotionService.getBetween(currentUser.getId(), start, end);

        return ResponseEntity.ok(sigMotionResponses);
    }
}
