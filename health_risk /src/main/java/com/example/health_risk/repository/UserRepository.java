package com.example.health_risk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.health_risk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // **✅ Kullanıcı adını kullanarak kullanıcıyı bul**
    Optional<User> findByUsername(String username);

    // **✅ E-posta adresine göre kullanıcıyı bul**
    Optional<User> findByEmail(String email);

    // **✅ Kullanıcı adı veya e-posta ile kullanıcı var mı kontrol et**
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}