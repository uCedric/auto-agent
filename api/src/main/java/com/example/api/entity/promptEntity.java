package com.example.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "prompts", schema = "api")
public class promptEntity {

    @Id
    @Column(name = "uuid", nullable = false, unique = true)
    @GeneratedValue
    @UuidGenerator
    private UUID uuid;

    @Column(name = "user_uuid", nullable = false)
    private String user_uuid;

    @Column(name = "content", nullable = false)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
