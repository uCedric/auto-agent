package com.example.api.repository;

import com.example.api.entity.documentEntity;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<documentEntity, UUID> {

    @Transactional
    @Modifying // This tells Spring Data JPA that this is an INSERT/UPDATE/DELETE query
    @Query(value = "INSERT INTO documents (uuid, user_uuid, s3_path) VALUES (:uuid, :user_uuid, :s3_path)", nativeQuery = true)
    void addDocument(@Param("uuid") UUID uuid, @Param("userUuid") UUID user_uuid, @Param("s3Path") String s3_path);
}
