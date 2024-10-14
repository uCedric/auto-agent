package com.hades.api.services.external;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.hades.api.utils.Exceptions.ExternalServerException;
import com.hades.api.utils.Exceptions.InternalServerException;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class AwsService {

    private S3Client s3Client;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.bucket}")
    private String bucket;

    @PostConstruct
    public void initializeAwsService() {
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.US_EAST_1)
                .build();
    }

    public void uploadFile(String s3Path, MultipartFile file) {
        try {

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(s3Path)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (S3Exception error) {
            throw new ExternalServerException("s3 bucket error: " + error);
        } catch (IOException error) {
            throw new InternalServerException("multipart file I/O error: " + error);
        }
    }

    public void downloadFile(String keyName, Path downloadPath) {
        try {
            s3Client.getObject(builder -> builder.bucket(bucket).key(keyName),
                    downloadPath);
        } catch (S3Exception e) {
            throw new ExternalServerException("s3 bucket error");
        }
    }
}
