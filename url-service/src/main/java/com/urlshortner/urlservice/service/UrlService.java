package com.urlshortner.urlservice.service;

import com.urlshortner.urlservice.dto.CreateUrlRequest;
import com.urlshortner.urlservice.dto.CreateUrlResponse;
import com.urlshortner.urlservice.entity.UrlMapping;
import com.urlshortner.urlservice.repository.UrlMappingRepository;
import com.urlshortner.urlservice.utility.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlMappingRepository urlMappingRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final StringRedisTemplate redisTemplate;

    private static final String CACHE_PREFIX = "url:";

    public String getOriginalUrl(String shortCode){

        String cacheKey = CACHE_PREFIX + shortCode;

        log.info("Checking Redis cache for shortCode: {}", shortCode);

        String cachedUrl = redisTemplate.opsForValue().get(cacheKey);

        if(cachedUrl!=null){
            log.info("Redis cache HIT for shortCode: {}", shortCode);
            return cachedUrl;
        }

        log.info("Redis cache MISS for shortCode: {}. Fetching from DB", shortCode);

        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(()-> new RuntimeException("Short URL not found"));

        if(mapping.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Short URL expired");
        }

        redisTemplate.opsForValue().set(
                cacheKey,
                mapping.getOriginalUrl(),
                Duration.ofHours(24)
        );

        log.info("Stored shortCode {} in Redis cache", shortCode);

        return mapping.getOriginalUrl();
    }

    public CreateUrlResponse createShortUrl(CreateUrlRequest request){
        String shortCode;

        do{
            shortCode = shortCodeGenerator.generateCode(7);
        }while (urlMappingRepository.existsByShortCode(shortCode));

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusYears(1))
                .clickCount(0L)
                .build();

        urlMappingRepository.save(urlMapping);

        return CreateUrlResponse.builder()
                .originalUrl(urlMapping.getOriginalUrl())
                .shortCode(urlMapping.getShortCode())
                .shortUrl("http://localhost:8080/"  + urlMapping.getShortCode())
                .build();
    }


}
