package com.shorty.shorty_backend.Controller;

import com.shorty.shorty_backend.Service.ShortyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShortyController {

    @Autowired
    private ShortyService shortservice;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        longUrl = longUrl.trim();  // Remove any extra spaces
        if (longUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid URL provided");
        }

            String shortCode = shortservice.shortUrl(longUrl);

        String shortUrl = "http://localhost:8080/api/s/" + shortCode;
        return ResponseEntity.ok(shortUrl);
    }
    @GetMapping("/s/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String longUrl = shortservice.getcode(code);
        if (longUrl == null) {
            return ResponseEntity.notFound().build(); // URL not found
        }
        return ResponseEntity.status(302).header("Location", longUrl).build();
    }
}
