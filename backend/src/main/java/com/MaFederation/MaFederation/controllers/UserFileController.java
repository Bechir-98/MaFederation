package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.User.UserFileContent;
import com.MaFederation.MaFederation.dto.User.UserFileDTO;
import com.MaFederation.MaFederation.services.UserFilesServices;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/userfiles")
public class UserFileController {

    private final UserFilesServices userFilesServices;

    public UserFileController(UserFilesServices userFilesServices) {
        this.userFilesServices = userFilesServices;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("userId") Integer userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {

        try {
            userFilesServices.saveFile(userId, file, type);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid file type: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<UserFileDTO>> getUserFiles(@RequestParam("userId") Integer userId) {
        try {
            List<UserFileDTO> files = userFilesServices.getFilesByUserId(userId);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

        @GetMapping("/file")
public ResponseEntity<UserFileContent> getFileContent(@RequestParam("fileId") Integer fileId) {
    try {
        UserFileContent content = userFilesServices.getFileContent(fileId);
        return ResponseEntity.ok(content);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}

    @PutMapping("/file/update")
    public ResponseEntity<UserFileDTO> updateFile(
            @RequestParam("fileId") Integer fileId,
            @RequestParam("file") MultipartFile file) {

        try {
            UserFileDTO updated = userFilesServices.updateFile(fileId, file);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("fileId") Integer fileId) {
        try {
            userFilesServices.deleteFile(fileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
