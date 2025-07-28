package com.dominik.control.kidshieldbackend.controller;

import com.dominik.control.kidshieldbackend.dto.AuthResponse;
import com.dominik.control.kidshieldbackend.dto.LoginRequest;
import com.dominik.control.kidshieldbackend.dto.RefreshTokenRequest;
import com.dominik.control.kidshieldbackend.dto.RegisterRequest;
import com.dominik.control.kidshieldbackend.model.RefreshToken;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.security.KidShieldUserDetails;
import com.dominik.control.kidshieldbackend.service.AuthenticationService;
import com.dominik.control.kidshieldbackend.service.RefreshTokenService;
import com.dominik.control.kidshieldbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        String tokenValue = authenticationService.generateToken(userDetails);

        Long userId = ((KidShieldUserDetails) userDetails).getId();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .refreshToken(refreshToken.getToken())
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request){
        UUID requestRefreshToken = request.getRefreshToken();

        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(requestRefreshToken);

        User user = newRefreshToken.getUser();
        UserDetails userDetails = new KidShieldUserDetails(user);
        String newAccessToken = authenticationService.generateToken(userDetails);


        return ResponseEntity.ok(AuthResponse.builder()
                        .token(newAccessToken)
                        .refreshToken(newRefreshToken.getToken())
                        .build());
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){

        User user = userService.createUser(registerRequest);

        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Logs out the user from the current device.
     * This endpoint requires the client to send its refresh token in the body.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.logout(request.getRefreshToken());
        return ResponseEntity.ok("User logged out successfully from this device.");
    }

    /**
     * Logs out the user from all devices.
     * This also requires the client's current refresh token to identify the user.
     */
    @PostMapping("/logout-all")
    public ResponseEntity<String> logoutAll(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.logoutAll(request.getRefreshToken());
        return ResponseEntity.ok("User logged out successfully from all devices.");
    }

}
