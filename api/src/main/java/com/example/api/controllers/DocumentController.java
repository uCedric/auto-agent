package com.example.api.controllers;

import com.example.api.services.DocumentService;
import com.example.api.utils.SuccessResponse;
import com.example.api.validators.AuthValidator;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private BodyValidator bodyValidator;

    @PostMapping
    public SuccessResponse<Map<String, String>> uploadChunks(@RequestHeader("Authorization") String token,
            @RequestParam MultipartFile[] files, HttpServletResponse response)
            throws InterruptedException, ExecutionException {

        authValidator.validateToken(token, response);

        bodyValidator.multipartValidate(files);

        documentService.addDocuments(response.getHeader("userUuid"), files).get();

        return new SuccessResponse<>(200, "Successfully uploaded documents");
    }

}
