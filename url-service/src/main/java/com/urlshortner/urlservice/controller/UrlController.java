package com.urlshortner.urlservice.controller;

import com.urlshortner.urlservice.dto.CreateUrlRequest;
import com.urlshortner.urlservice.dto.CreateUrlResponse;
import com.urlshortner.urlservice.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<CreateUrlResponse> createShortUrl(@Valid @RequestBody CreateUrlRequest request){
        return ResponseEntity.ok(urlService.createShortUrl(request));
    }
}
