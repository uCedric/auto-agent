package com.example.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.example.api.entity.userEntity;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<userEntity, UUID> {

    @Query(value = "SELECT COUNT(email) FROM users WHERE email = :email", nativeQuery = true)
    int searchUserByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (id, name, email, password) VALUES (:id, :name, :email, :password)", nativeQuery = true)
    int addUser(@Param("id") UUID id, @Param("name") String name, @Param("email") String email,
            @Param("password") String password);
}
