package com.yumyumcoach.global.health;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class DbHealthController {
    private final DbHealthService dbHealthService;

    @GetMapping("/db")
    public ResponseEntity<Map<String, String>> checkDbHealth() {
        boolean up = dbHealthService.isDbUp();

        Map<String, String> body = new HashMap<>();
        body.put("status", up ? "UP" : "DOWN");

        if (up) {
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
}
