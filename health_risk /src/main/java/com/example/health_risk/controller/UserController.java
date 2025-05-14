package com.example.health_risk.controller;

import com.example.health_risk.model.User;
import com.example.health_risk.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5501") // Frontend için CORS ayarı
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Şifreleme mekanizması
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Kullanıcı adı ve e-posta güncelleme
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());

            // Eğer şifre değiştiriliyorsa, şifreyi hashleyelim
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("{\"message\": \"Kullanıcı bulunamadı!\"}");
        }
    }
}