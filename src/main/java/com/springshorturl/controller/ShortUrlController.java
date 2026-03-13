package com.springshorturl.controller;

import com.springshorturl.dto.CreateShortUrlRequest;
import com.springshorturl.dto.CreateShortUrlResponse;
import com.springshorturl.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
