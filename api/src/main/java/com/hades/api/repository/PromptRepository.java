package com.hades.api.repository;

import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.hades.api.entity.promptEntity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PromptRepository extends JpaRepository<promptEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO prompts (uuid, user_uuid, content) VALUES (:uuid, :userUuid, :content)", nativeQuery = true)
    int addPrompt(@Param("uuid") UUID uuid, @Param("userUuid") UUID userUuid,
            @Param("content") String content);
}
