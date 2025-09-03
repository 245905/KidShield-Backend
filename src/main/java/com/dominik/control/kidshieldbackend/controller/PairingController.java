package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.GenerateCodeResponse;
import com.dominik.control.kidshieldbackend.dto.PairByPinRequest;
import com.dominik.control.kidshieldbackend.dto.PairByUUIDRequest;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.security.annotation.CurrentUser;
import com.dominik.control.kidshieldbackend.service.PairingCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/pair")
@RequiredArgsConstructor
public class PairingController {

    public final PairingCodeService pairingCodeService;

    @PreAuthorize("hasRole('MONITORED')")
    @PostMapping(path = "/code")
    public ResponseEntity<GenerateCodeResponse> generatePairingCode(@CurrentUser KidShieldUserDetails currentUser){
        Long id = currentUser.getId();
        GenerateCodeResponse response = pairingCodeService.generateCode(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping(path = "/qr")
    public ResponseEntity<?> pairAccountsByUUID(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody PairByUUIDRequest request
    ){
        Long id = currentUser.getId();
        pairingCodeService.pairByUUID(request, id);
        return ResponseEntity.ok("Successfully paired");
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping(path = "/pin")
    public ResponseEntity<?> pairAccountsByPin(
            @CurrentUser KidShieldUserDetails currentUser,
            @RequestBody PairByPinRequest request
    ){
        Long id = currentUser.getId();
        pairingCodeService.pairByPin(request, id);
        return ResponseEntity.ok("Successfully paired");
    }
}
