package com.example.api.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents", schema = "api")
public class documentEntity {

    @Id
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "user_uuid", nullable = false)
    private UUID user_uuid;

    @Column(name = "s3_path", nullable = false)
    private String s3_path;

    public void setS3Path(String s3_path) {
        this.s3_path = s3_path;
    }

    public String getS3Path() {
        return s3_path;
    }
}
