package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.AppInfoRequest;
import com.dominik.control.kidshieldbackend.dto.AppInfoResponse;
import com.dominik.control.kidshieldbackend.dto.MessageResponse;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appInfo")
@RequiredArgsConstructor
public class AppInfoController {

    public final AppInfoService appInfoService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping
    public ResponseEntity<MessageResponse> pushData(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody List<AppInfoRequest> appInfoRequests
    ){

        appInfoService.storeData(currentUser.getId(), appInfoRequests);
        return ResponseEntity.ok(new MessageResponse("Successfully stored data"));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<AppInfoResponse>> pullData(
            @CurrentUser KidShieldUserDetails currentUser
    ){

        List<AppInfoResponse> appInfoResponses = appInfoService.retrieveData(currentUser.getId());

        return ResponseEntity.ok(appInfoResponses);
    }

}
