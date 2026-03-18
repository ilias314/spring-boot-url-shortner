package com.springshorturl.controller;

import com.springshorturl.entity.ShortUrl;
import com.springshorturl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class RedirectController {

    @Autowired
    private ShortUrlRepository repository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = redisTemplate.opsForValue().get(shortCode);
        if (originalUrl == null) {
            Optional<ShortUrl> opt = repository.findByShortCode(shortCode);
            if (!opt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            ShortUrl url = opt.get();
            if (url.getExpiresAt() != null && url.getExpiresAt().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
            originalUrl = url.getOriginalUrl();
            url.setClickCount(url.getClickCount() + 1);
            repository.save(url);
            redisTemplate.opsForValue().set(shortCode, originalUrl);
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, originalUrl).build();
    }
}
