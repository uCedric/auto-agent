package com.hades.api.services;

import com.hades.api.dtos.Dto;
import com.hades.api.repository.DocumentRepository;
import com.hades.api.services.external.AwsService;
import com.hades.api.services.external.llmService;
import com.hades.api.utils.dataFormate;
import com.hades.api.utils.Exceptions.InvalidParameterException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import java.util.UUID;
import java.util.Map;
import org.springframework.data.domain.Pageable;

@Service
public class DocumentService {

    @Autowired
    private llmService llmService;

    @Autowired
    private AwsService awsService;

    @Autowired
    private DocumentRepository documentRepository;

    @Async("TaskThread")
    public CompletableFuture<String> addDocuments(String userUuid, MultipartFile[] files) {

        for (MultipartFile file : files) {
            processDocument(userUuid, file);
        }

        return CompletableFuture.completedFuture("Successfully uploaded documents");
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

    @Transactional(rollbackOn = Exception.class)
    public void processDocument(String userUuid, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        List<String> chunks = sliceFileIntoChunks(file);

        try {
            String s3Path = dataFormate.makeS3Path(userUuid, file.getOriginalFilename());
            awsService.uploadFile(s3Path, file);

            documentRepository.addDocument(uuid, UUID.fromString(userUuid), file.getOriginalFilename(), s3Path);

            llmService.addChunks(uuid, chunks);
        } catch (Exception e) {
            throw new InvalidParameterException("could not add document: " + e);
        }
    }

    @Async("TaskThread")
    public CompletableFuture<Object> getDocuments(Dto getFilesDto) {
        Map<String, Object> getFileAttr = getFilesDto.getAttributes();

        UUID userUuidFromString = UUID.fromString(getFileAttr.get("userUuid").toString());

        Pageable pageble = PageRequest.of((int) getFileAttr.get("offset"), (int) getFileAttr.get("limit"));
        List<String> documents = documentRepository.getDocuments(userUuidFromString, pageble);

        return CompletableFuture.completedFuture(documents);
    }
}
