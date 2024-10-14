package com.hades.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hades.api.entity.userEntity;

import org.springframework.data.repository.query.Param;

import java.util.UUID;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<userEntity, UUID> {

    @Query(value = "SELECT COUNT(email) FROM users WHERE email = :email", nativeQuery = true)
    int searchUserCountByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (uuid, name, email, password) VALUES (:id, :name, :email, :password)", nativeQuery = true)
    int addUser(@Param("id") UUID id, @Param("name") String name, @Param("email") String email,
            @Param("password") String password);

    @Query(value = "SELECT password FROM users WHERE email = :email", nativeQuery = true)
    String getUserPasswordByEmail(@Param("email") String email);

    @Query(value = "SELECT uuid FROM users WHERE email = :email", nativeQuery = true)
    UUID getUuidByEmail(@Param("email") String email);
}
