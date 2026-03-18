package com.springshorturl.controller;

import com.springshorturl.dto.CreateShortUrlRequest;
import com.springshorturl.dto.CreateShortUrlResponse;
import com.springshorturl.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springshorturl.dto.GetShortUrlMetadataResponse;
import com.springshorturl.dto.GetShortUrlAnalyticsResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {

    @Autowired
    private ShortUrlService service;

    @PostMapping
    public ResponseEntity<CreateShortUrlResponse> create(@Valid @RequestBody CreateShortUrlRequest request) {
        CreateShortUrlResponse response = service.createShortUrl(request.getOriginalUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<GetShortUrlMetadataResponse> getMetadata(@PathVariable String shortCode) {
        Optional<GetShortUrlMetadataResponse> response = service.getShortUrlMetadata(shortCode);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{shortCode}/analytics")
    public ResponseEntity<GetShortUrlAnalyticsResponse> getAnalytics(@PathVariable String shortCode) {
        Optional<GetShortUrlAnalyticsResponse> response = service.getShortUrlAnalytics(shortCode);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
