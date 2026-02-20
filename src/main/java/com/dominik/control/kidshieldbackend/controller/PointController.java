package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.MessageResponse;
import com.dominik.control.kidshieldbackend.dto.PointRequest;
import com.dominik.control.kidshieldbackend.dto.PointResponse;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<PointRequest> pointRequests
    ){

        pointService.storeData(currentUser.getId(), pointRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/time/{time}")
    public ResponseEntity<List<PointResponse>> pullRecentData(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long time
    ){

        List<PointResponse> pointResponses = pointService.getRecent(currentUser.getId(), time);

        return ResponseEntity.ok(pointResponses);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/start/{start}/end/{end}")
    public ResponseEntity<List<PointResponse>> pullDataBetween(
            @CurrentUser KidShieldUserDetails currentUser,
            @PathVariable Long start,
            @PathVariable Long end
    ){

        List<PointResponse> pointResponses = pointService.getBetween(currentUser.getId(), start, end);

        return ResponseEntity.ok(pointResponses);
    }
}
