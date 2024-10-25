package com.hades.api.controllers;

import com.hades.api.services.DocumentService;
import com.hades.api.utils.AsyncExecutor;
import com.hades.api.utils.AsyncService;
import com.hades.api.utils.SuccessResponse;
import com.hades.api.validators.AuthValidator;
import com.hades.api.validators.BodyValidator;
import com.hades.api.dtos.Dto;
import com.hades.api.dtos.getFilesDto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private AsyncExecutor<Dto, Object> asyncExecutor = AsyncExecutor.<Dto, Object>init();

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private BodyValidator bodyValidator;

    @PostMapping
    public SuccessResponse<Map<String, String>> addDocuments(@RequestHeader("Authorization") String token,
            @RequestParam MultipartFile[] files, HttpServletResponse response)
            throws InterruptedException, ExecutionException {
        authValidator.validateToken(token, response);

        bodyValidator.multipartValidate(files);

        documentService.addDocuments(response.getHeader("userUuid"), files).get();

        return new SuccessResponse<>(200, "Successfully uploaded documents");
    }

    @PostMapping("/preload/{userUuid}")
    public SuccessResponse<List<String>> getDocuments(@RequestHeader("Authorization") String token,
            @PathVariable("userUuid") String userUuid,
            @RequestBody getFilesDto getFilesDto,
            HttpServletResponse response) throws InterruptedException, ExecutionException {
        authValidator.validateToken(token, response);

        getFilesDto.setUserUuid(userUuid);
        AsyncService<Dto, Object> asyncService = AsyncService.<Dto, Object>init()
                .addTask(documentService::getDocuments, getFilesDto);

        Map<String, Object> serviceResult = asyncExecutor.process(asyncService).get();

        List<String> documents = (List<String>) serviceResult.get("task1");

        SuccessResponse<List<String>> successResponse = new SuccessResponse<List<String>>(200, "success", documents);

        return successResponse;
    }

}
