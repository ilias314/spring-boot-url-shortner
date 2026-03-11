package com.springshorturl.util;

import com.springshorturl.repository.ShortUrlRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Utility class for generating unique short codes.
 * Generates 6-8 character alphanumeric codes with collision handling.
 */
@Component
public class shortCodeGenerator {

    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 8;
    private static final int MAX_RETRIES = 5;
    private static final Random RANDOM = new Random();

    private final ShortUrlRepository shortUrlRepository;

    public shortCodeGenerator(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    /**
     * Generates a unique short code with collision handling.
     * Retries up to MAX_RETRIES times if collisions occur.
     *
     * @return A unique short code
     * @throws IllegalStateException if unable to generate unique code after max retries
     */
    public String generateShortCode() {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            String code = generateRandomCode();
            if (!shortUrlRepository.existsByShortCode(code)) {
                return code;
            }
        }

        // If all retries failed, throw an exception
        throw new IllegalStateException(
            "Failed to generate unique short code after " + MAX_RETRIES + " attempts"
        );
    }

    /**
     * Generates a random short code of length between MIN_LENGTH and MAX_LENGTH.
     *
     * @return A random alphanumeric code
     */
    private String generateRandomCode() {
        int length = MIN_LENGTH + RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(CHARSET.charAt(RANDOM.nextInt(CHARSET.length())));
        }

        return code.toString();
    }
}
