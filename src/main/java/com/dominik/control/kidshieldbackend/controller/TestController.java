package com.dominik.control.kidshieldbackend.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping
    public ResponseEntity<?> open(){
        logger.info("Successful call to open endpoint");
        return ResponseEntity.ok("Successful call to open endpoint");
    }

    @PostMapping
    public ResponseEntity<?> closed(){
        logger.info("Successful call to closed endpoint");
        return ResponseEntity.ok("Successful call to closed endpoint");
    }

    @PreAuthorize("hasRole('MONITORED')")
    @DeleteMapping
    public ResponseEntity<?> restricted(){
        logger.info("Successful call to restricted endpoint");
        return ResponseEntity.ok("Successful call to restricted endpoint");
    }

}
