package com.foodanalyzer.foodanalyzer.controller;

import com.foodanalyzer.foodanalyzer.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeFood(@RequestParam("image") MultipartFile image) {
        String result = foodService.analyzeImage(image);
        return ResponseEntity.ok().
                contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
