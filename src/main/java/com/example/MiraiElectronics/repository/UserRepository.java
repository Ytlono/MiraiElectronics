package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    boolean existsByEmailOrUsername(String email,String username);
}
