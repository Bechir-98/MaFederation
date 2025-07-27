package com.MaFederation.MaFederation.services;


import com.MaFederation.MaFederation.dto.User.UserPostDTO;
import com.MaFederation.MaFederation.mappers.UserFileMapper;
import com.MaFederation.MaFederation.mappers.UserMapper;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.UserFileRepository;
import com.MaFederation.MaFederation.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String createUser(UserPostDTO entity) {
        User user = userMapper.toEntity(entity);
        userRepository.save(user);
        return "User created successfully";
    }




    }

