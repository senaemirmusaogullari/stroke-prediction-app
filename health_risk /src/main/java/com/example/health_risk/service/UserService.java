package com.example.health_risk.service;

import com.example.health_risk.model.User;
import com.example.health_risk.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Kullanıcıyı kaydet (kayıt olma)
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kullanılıyor!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Bu e-posta adresi zaten kayıtlı!");
        }

        // ⚠️ Şifreyi güvenli şekilde encode et
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    // ✅ Giriş için kullanıcı doğrulama
    public boolean authenticateUser(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    // ✅ Kullanıcıyı getir
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}