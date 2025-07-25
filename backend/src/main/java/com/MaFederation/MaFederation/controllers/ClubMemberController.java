package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.model.*;
import com.MaFederation.MaFederation.repository.*;
import com.MaFederation.MaFederation.mappers.ClubMemberMapper;
import com.MaFederation.MaFederation.dto.ClubMember.ClubMemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin("*")
public class ClubMemberController {

    @Autowired private PlayerRepository playerRepository;
    @Autowired private StaffRepository staffRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private ClubMemberMapper clubMemberMapper;

    private static final String UPLOAD_DIR = "C:/MaFederation/uploads/";
    private final ObjectMapper objectMapper;

    public ClubMemberController() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @PostMapping(value = "/addmember", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addMember(
        @RequestParam("user") MultipartFile userFile,
        @RequestParam(value = "files", required = false) List<MultipartFile> files,
        @RequestParam(value = "fileTypes", required = false) List<String> fileTypes
    ) {
        try {
            // Parse the JSON from the uploaded file
            String userJson = new String(userFile.getBytes());
            System.out.println("Received JSON: " + userJson); // Debug log
            
            ClubMemberDTO userDto = objectMapper.readValue(userJson, ClubMemberDTO.class);
            
            // Convert DTO to entity using mapper
            ClubMember user = clubMemberMapper.toEntity(userDto);
            
            // Validate clubId before proceeding
            if (userDto.getClubId() != null && userDto.getClubId() <= 0) {
                return ResponseEntity.badRequest()
                    .body("Invalid club ID. Please select a valid club.");
            }
            
            // Generate username if not provided
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                String username = generateUsername(user.getFirstName(), user.getLastName(), user.getEmail());
                user.setUsername(username);
                System.out.println("Generated username: " + username);
            }
            
            // Set a default password hash for club members if not provided
            if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
                String tempPassword = "TempPass_" + UUID.randomUUID().toString().substring(0, 8);
                user.setPasswordHash(hashPassword(tempPassword));
                System.out.println("Generated temporary password for user: " + tempPassword);
            }
            
            ClubMember savedMember;

            // Save based on type
            switch (user.getType()) {
                case "PLAYER":
                    savedMember = playerRepository.save((Player) user);
                    break;
                case "STAFF":
                    savedMember = staffRepository.save((Staff) user);
                    break;
                case "ADMIN":
                    savedMember = adminRepository.save((Administration) user);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Unknown type: " + user.getType());
            }

            // Create directory for files if there are any files to upload
            if (files != null && !files.isEmpty()) {
                String dirPath = UPLOAD_DIR + savedMember.getUserId() + "/";
                File directory = new File(dirPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save files
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    if (!file.isEmpty()) {
                        String type = fileTypes.get(i);
                        String filename = savedMember.getUserId() + "_" + type + "_" + file.getOriginalFilename();
                        Path filePath = Paths.get(dirPath, filename);
                        Files.write(filePath, file.getBytes());
                    }
                }
            }

            return ResponseEntity.ok()
                .body("Member saved successfully with ID: " + savedMember.getUserId());

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
        }
    }

    // Generate username from name and email
    private String generateUsername(String firstName, String lastName, String email) {
        // Try to create username from first name + last name
        String baseUsername = "";
        if (firstName != null && !firstName.isEmpty()) {
            baseUsername += firstName.toLowerCase();
        }
        if (lastName != null && !lastName.isEmpty()) {
            baseUsername += "." + lastName.toLowerCase();
        }
        
        // If no name provided, use email prefix
        if (baseUsername.isEmpty() && email != null && email.contains("@")) {
            baseUsername = email.substring(0, email.indexOf("@")).toLowerCase();
        }
        
        // Add random suffix to ensure uniqueness
        baseUsername += "_" + UUID.randomUUID().toString().substring(0, 4);
        
        // Remove special characters and spaces
        return baseUsername.replaceAll("[^a-zA-Z0-9._]", "");
    }

    // Simple password hashing method (you should use BCrypt in production)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return "$SHA256$" + hexString.toString(); // Prefix to indicate hash type
        } catch (Exception e) {
            // Fallback to simple hash if SHA-256 fails
            return "$SIMPLE$" + (password.hashCode() + "SALT").hashCode();
        }
    }
}
