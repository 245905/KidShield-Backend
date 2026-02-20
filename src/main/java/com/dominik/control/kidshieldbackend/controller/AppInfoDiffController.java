package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.AppInfoDiffResponse;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.AppInfoDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appInfoDiff")
@RequiredArgsConstructor
public class AppInfoDiffController {

    private final AppInfoDiffService appInfoDiffService;

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<AppInfoDiffResponse>> pullData(
            @CurrentUser KidShieldUserDetails currentUser
    ){

        List<AppInfoDiffResponse> appInfoDiffResponses = appInfoDiffService.retrieveData(currentUser.getId());

        return ResponseEntity.ok(appInfoDiffResponses);
    }
}
