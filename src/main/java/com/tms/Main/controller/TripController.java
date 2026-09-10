package com.tms.Main.controller;

import com.tms.Main.Dto.TripResponse;
import com.tms.Main.Dto.TripTransactionRequest;
import com.tms.Main.Service.TripCreationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
@AllArgsConstructor
public class TripController {

    private final TripCreationService tripService;
    @PostMapping
    public ResponseEntity<Object> createTrip(
            @RequestBody @Valid TripTransactionRequest request) {

       return ResponseEntity.ok( tripService.createTrip(request));
    }

}
