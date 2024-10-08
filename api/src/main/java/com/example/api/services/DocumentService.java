package com.example.api.services;

import com.example.api.services.external.llmService;
import com.example.api.services.external.AwsService;
import com.example.api.utils.Exceptions.InvalidParameterException;
import com.example.api.repository.DocumentRepository;
import com.example.api.utils.dataFormate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private llmService llmService;

    @Autowired
    private AwsService awsService;

    @Autowired
    private DocumentRepository documentRepository;

    @Async("dbAsyncExecutor")
    public CompletableFuture<String> addDocuments(String userUuid, MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {

                UUID uuid = UUID.randomUUID();
                List<String> chunks = sliceFileIntoChunks(file);

                String s3Path = dataFormate.makeS3Path(userUuid, file.getOriginalFilename());
                awsService.uploadFile(s3Path, file);

                documentRepository.addDocument(uuid, UUID.fromString(userUuid), s3Path);

                llmService.addChunks(uuid, chunks);
            } catch (Exception e) {
                throw new InvalidParameterException("could not add document: " + e);
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    public List<String> sliceFileIntoChunks(MultipartFile file) {
        List<String> chunkArray = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                chunkArray.add(line);
            }
        } catch (Exception e) {
            throw new InvalidParameterException("could not read file");
        }

        return chunkArray;
    }
}
