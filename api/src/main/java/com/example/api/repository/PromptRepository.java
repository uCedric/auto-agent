package com.example.api.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.api.entity.promptEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PromptRepository extends JpaRepository<promptEntity, UUID> {

    @Query(value = "SELECT content FROM prompts p WHERE p.uuid = 'bff72df2-d3a6-4287-a22f-ddffccc69c97'", nativeQuery = true)
    String findByPromptByUuid(@Param("uuid") UUID uuid);
}
