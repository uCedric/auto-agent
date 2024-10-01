package com.example.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @PostMapping("/upload")
    public String uploadChunks(@RequestParam("files") MultipartFile[] files) {
        // String uploadedFileName = Arrays.stream(files).map(x ->
        // x.getOriginalFilename())
        // .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        return "success";
    }

}
