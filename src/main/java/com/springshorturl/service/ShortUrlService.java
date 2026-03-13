package com.springshorturl.service;

import com.springshorturl.dto.CreateShortUrlResponse;
import com.springshorturl.entity.ShortUrl;
import com.springshorturl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository repository;

    public CreateShortUrlResponse createShortUrl(String originalUrl) {
        String shortCode = generateUniqueShortCode();
        ShortUrl entity = new ShortUrl();
        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(shortCode);
        repository.save(entity);
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new CreateShortUrlResponse(shortCode, shortUrl);
    }

    private String generateUniqueShortCode() {
        int retries = 5;
        while (retries > 0) {
            String code = generateRandomCode();
            if (!repository.existsByShortCode(code)) {
                return code;
            }
            retries--;
        }
        throw new RuntimeException("Could not generate unique code");
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int length = 6 + random.nextInt(3); // 6-8 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
