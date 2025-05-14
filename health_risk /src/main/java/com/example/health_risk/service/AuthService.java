package com.example.health_risk.service;

import com.example.health_risk.model.User;
import com.example.health_risk.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Kullanıcı kaydetme
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten mevcut!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Bu e-posta adresi zaten kullanılıyor!");
        }

        // Şifreyi şifreleyerek kaydediyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ✅ Kullanıcı giriş kontrolü
    public User login(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı!");
        }

        User user = optionalUser.get();

        // Şifreyi doğrula
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Şifre yanlış!");
        }

        return user;
    }
}