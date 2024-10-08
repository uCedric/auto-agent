package com.example.api.services;

import com.example.api.services.external.llmService;
import com.example.api.services.external.AwsService;
import com.example.api.utils.Exceptions.InvalidParameterException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    @Autowired
    private llmService llmService;

    @Autowired
    private AwsService awsService;

    @Async("dbAsyncExecutor")
    public CompletableFuture<String> addDocuments(String userEmail, MultipartFile[] files) {
        List<String> chunks = sliceFileIntoChunks(files);

        String result = llmService.addChunks(chunks);
        awsService.uploadFile(userEmail, files);
        // TODO: add document to db

        return CompletableFuture.completedFuture(result);
    }

    public List<String> sliceFileIntoChunks(MultipartFile[] files) {
        List<String> chunkArray = new ArrayList<String>();

        for (MultipartFile file : files) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    chunkArray.add(line);
                }
            } catch (Exception e) {
                throw new InvalidParameterException("could not read file");
            }
        }

        return chunkArray;
    }
}
