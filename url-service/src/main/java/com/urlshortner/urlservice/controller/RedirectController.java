package com.urlshortner.urlservice.controller;

import com.urlshortner.urlservice.event.UrlClickedEvent;
import com.urlshortner.urlservice.producer.UrlClickProducer;
import com.urlshortner.urlservice.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RedirectController {
    private final UrlService urlService;
    private final UrlClickProducer urlClickProducer;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode,
            HttpServletRequest request){

        String originalUrl = urlService.getOriginalUrl(shortCode);

        log.info("About to send Kafka event for shortCode: {}", shortCode);

        urlClickProducer.sendClickEvent(
                UrlClickedEvent.builder()
                        .shortCode(shortCode)
                        .originalUrl(originalUrl)
                        .clickedAt(LocalDateTime.now())
                        .ipAddress(request.getRemoteAddr())
                        .userAgent(request.getHeader("User-Agent"))
                        .build()
        );

        log.info("Producer method call completed for shortCode: {}", shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
