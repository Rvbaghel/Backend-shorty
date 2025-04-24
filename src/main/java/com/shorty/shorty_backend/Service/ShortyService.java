package com.shorty.shorty_backend.Service;

import com.shorty.shorty_backend.Entity.ShortUrl;
import com.shorty.shorty_backend.Repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class ShortyService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    private HashMap<String,String> urlmap=new HashMap<>();

    public String shortUrl(String longUrl){

        longUrl = longUrl.replaceAll("\"", "").trim();

        String shortCode=generatedRandomCode();
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setLongUrl(longUrl);
        shortUrlRepository.save(shortUrl);
        return shortCode;
    }

    public String getcode(String shortCode){
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode);
        if (shortUrl != null) {
            // Clean the URL just in case it has any remaining quotes
            String cleanedUrl = shortUrl.getLongUrl().replaceAll("\"", "").trim();
            return cleanedUrl;
        }
        return null;
    }
    private String generatedRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortcode=new StringBuilder();
        Random ram=new Random();

        for (int i = 0; i < 6; i++) {
            shortcode.append(chars.charAt(ram.nextInt(chars.length())));
        }

        return  shortcode.toString();
    }
}
