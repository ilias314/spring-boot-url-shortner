package com.springshorturl.service;

import com.springshorturl.dto.CreateShortUrlResponse;
import com.springshorturl.entity.ShortUrl;
import com.springshorturl.repository.ShortUrlRepository;
import com.springshorturl.util.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


import java.security.SecureRandom;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository repository;

    @Autowired
    private ShortCodeGenerator shortCodeGenerator;

    public CreateShortUrlResponse createShortUrl(String originalUrl) {
        String shortCode = shortCodeGenerator.generateShortCode();
        ShortUrl entity = new ShortUrl();
        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(shortCode);
        entity.setCreatedAt(LocalDateTime.now());
        repository.save(entity);
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new CreateShortUrlResponse(shortCode, shortUrl);
    }


}
