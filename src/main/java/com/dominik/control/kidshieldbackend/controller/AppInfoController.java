package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.AppInfoRequest;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appInfo")
@RequiredArgsConstructor
public class AppInfoController {

    public final AppInfoService appInfoService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<?> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<AppInfoRequest> appInfoRequests
            ){

        appInfoService.storeData(currentUser.getId(), appInfoRequests);
        return ResponseEntity.ok("");
    }
}
