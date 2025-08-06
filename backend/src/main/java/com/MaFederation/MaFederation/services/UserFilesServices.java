package com.MaFederation.MaFederation.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MaFederation.MaFederation.dto.User.UserFileContent;
import com.MaFederation.MaFederation.dto.User.UserFileDTO;
import com.MaFederation.MaFederation.enums.UserFileType;
import com.MaFederation.MaFederation.mappers.UserFileMapper;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import com.MaFederation.MaFederation.repository.UserFileRepository;
import com.MaFederation.MaFederation.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserFilesServices {


    private final UserFileMapper UserFileMapper;
    private final UserFileRepository UserFileRepository;
    private final UserRepository UserRepository;


    public UserFilesServices (UserFileMapper UserFileMapper,UserFileRepository UserFileRepository,UserRepository UserRepository)
    {
        this.UserFileMapper= UserFileMapper;
        this.UserFileRepository=UserFileRepository;
        this.UserRepository=UserRepository;

    }


  public void saveFile(Integer userId, MultipartFile file, String typeStr) throws IOException {
    // Step 1: Fetch the user or throw an exception
    User user = UserRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    // Step 2: Convert the type string to enum safely
    UserFileType type;
    try {
        type = UserFileType.valueOf(typeStr.toUpperCase());
    } catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException("Invalid file type: " + typeStr);
    }

    // Step 3: Build and save the UserFile
    UserFile userFile = UserFile.builder()
        .type(type)
        .content(file.getBytes())
        .user(user)
        .build();

    UserFileRepository.save(userFile);
}




 @Transactional
    public List<UserFileDTO> getFilesByUserId(Integer userId) {
        User user = UserRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return UserFileRepository.findByUser(user).stream()
            .map(UserFileMapper::toDto)
            .toList();
    }

    @Transactional
    public UserFileContent getFileContent(Integer fileId) {
        UserFile userFile = UserFileRepository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));
        
        return new UserFileContent(userFile.getContent());
    }

    @Transactional
    public UserFileDTO updateFile(Integer fileId, MultipartFile file) {
        User user = UserRepository.findByFiles_Id(fileId); // custom method required
        if (user == null) {
            throw new RuntimeException("User not found for the file id");
        }

        UserFile userFile = UserFileRepository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));

        try {
            userFile.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file content", e);
        }

        userFile.setUser(user);
        UserFileRepository.save(userFile);

        return UserFileMapper.toDto(userFile);
    }

    @Transactional
    public void deleteFile(Integer fileId) {
        if (!UserFileRepository.existsById(fileId)) {
            throw new RuntimeException("File not found with ID: " + fileId);
        }
        UserFileRepository.deleteById(fileId);
    }
}
