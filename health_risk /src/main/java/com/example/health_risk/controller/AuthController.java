package com.example.health_risk.controller;

import com.example.health_risk.model.User;
import com.example.health_risk.security.JwtService;
import com.example.health_risk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // ✅ Kullanıcı Kayıt
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kayıt başarılı!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // ✅ JWT ile Kullanıcı Giriş
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        boolean isAuthenticated = userService.authenticateUser(username, password);

        if (isAuthenticated) {
            String token = jwtService.generateToken(username); // 🔐 Gerçek token üretimi
            Map<String, String> response = new HashMap<>();
            response.put("message", "Giriş başarılı!");
            response.put("token", token); // ✅ Gerçek JWT
            response.put("username", username);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Kullanıcı adı veya şifre hatalı!");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    // ✅ Çıkış
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Çıkış başarılı!");
        return ResponseEntity.ok(response);
    }
}