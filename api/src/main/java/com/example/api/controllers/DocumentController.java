package com.example.api.controllers;

import com.example.api.services.DocumentService;
import com.example.api.utils.SuccessResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public SuccessResponse<Map<String, String>> uploadChunks(@RequestParam MultipartFile[] files)
            throws InterruptedException, ExecutionException {
        documentService.addDocuments(files).get();

        SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                "Successfully uploaded documents");

        return response;
    }

}
