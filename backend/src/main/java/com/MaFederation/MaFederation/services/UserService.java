package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.UserDTO;
import com.MaFederation.MaFederation.dto.UserFileDTO;
import com.MaFederation.MaFederation.mappers.UserFileMapper;
import com.MaFederation.MaFederation.mappers.UserMapper;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.model.UserFile;
import com.MaFederation.MaFederation.repository.UserFileRepository;
import com.MaFederation.MaFederation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFileRepository userFileRepository;
    private final UserMapper userMapper;
    private final UserFileMapper userFileMapper;

    /*-----------------------------------------------------
     * Create User with linked file IDs
     *-----------------------------------------------------*/
    @Transactional
    public User createUser(UserDTO dto) {
        User user = userMapper.fromDto(dto);

        // Handle files (by ID)
        if (dto.getFileIds() != null && !dto.getFileIds().isEmpty()) {
            List<UserFile> files = userFileRepository.findAllById(dto.getFileIds());
            if (files.size() != dto.getFileIds().size()) {
                throw new EntityNotFoundException("One or more file IDs are invalid");
            }

            files.forEach(file -> file.setUser(user));
            user.setFiles(files);
        }

        return userRepository.save(user);
    }

    /*-----------------------------------------------------
     * Get User DTO by ID
     *-----------------------------------------------------*/
    public UserDTO getUserDtoById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return userMapper.toDto(user);
    }

    /*-----------------------------------------------------
     * Get User entity by ID
     *-----------------------------------------------------*/
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    /*-----------------------------------------------------
     * Get all users
     *-----------------------------------------------------*/
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /*-----------------------------------------------------
     * Delete user by ID
     *-----------------------------------------------------*/
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    /*-----------------------------------------------------
     * Add a file to a user
     *-----------------------------------------------------*/
    @Transactional
    public UserFile addFileToUser(Integer userId, UserFileDTO fileDto) {
        User user = getUserById(userId);
        UserFile file = userFileMapper.toEntity(fileDto, user);
        return userFileRepository.save(file);
    }
}
