package com.dominik.control.kidshieldbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
public class TestController {


    @GetMapping
    public ResponseEntity<?> open(){
        return ResponseEntity.ok("");
    }

    @PostMapping
    public ResponseEntity<?> closed(){
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasRole('MONITORED')")
    @DeleteMapping
    public ResponseEntity<?> restricted(){
        return ResponseEntity.ok("");
    }

}
