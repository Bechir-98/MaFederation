package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.model.*;
import com.MaFederation.MaFederation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ClubMemberController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AdminRepository adminRepository;

    // Base folder where files will be saved, make sure this folder exists
    private static final String UPLOAD_DIR = "C:/MaFederation/uploads/";

    @PostMapping(value = "/addmember", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addMember(
            @RequestPart("user") ClubMember member,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("fileTypes") List<String> fileTypes) {

        try {
            ClubMember savedMember;

            switch (member.getType()) {
                case "PLAYER":
                    savedMember = playerRepository.save((Player) member);
                    break;
                case "STAFF":
                    savedMember = staffRepository.save((Staff) member);
                    break;
                case "ADMIN":
                    savedMember = adminRepository.save((Administration) member);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid type: " + member.getType());
            }

            // Save each file to disk
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String fileType = fileTypes.get(i);

                // Create directory for member files
                String memberDirPath = UPLOAD_DIR + savedMember.getUserId() + "/";
                File memberDir = new File(memberDirPath);
                if (!memberDir.exists()) {
                    memberDir.mkdirs();
                }

                // Create filename: memberId_fileType_originalName.ext
                String filename = savedMember.getUserId() + "_" + fileType + "_" + file.getOriginalFilename();

                // Save file to disk
                Path filePath = Paths.get(memberDirPath, filename);
                Files.write(filePath, file.getBytes());
            }

            return ResponseEntity.ok("Member saved with files on disk!");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File saving failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving member: " + e.getMessage());
        }
    }
}
