package com.example.api.controllers;

import com.example.api.services.DocumentService;
import com.example.api.utils.SuccessResponse;
import com.example.api.validators.AuthValidator;

import io.jsonwebtoken.Claims;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping
    public SuccessResponse<Map<String, String>> uploadChunks(@RequestHeader("Authorization") String token,
            @RequestParam MultipartFile[] files)
            throws InterruptedException, ExecutionException {
        // TODO: add Filter with jwt and body checker

        Claims tokenContent = authValidator.validateToken(token);
        // TODO: change tokenContent.get userUuid
        documentService.addDocuments(tokenContent.get("email", String.class), files).get();
        // upload s3
        //
        return new SuccessResponse<>(200, "Successfully uploaded documents");
    }

}
