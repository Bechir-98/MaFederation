package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.enums.RoleName;
import com.MaFederation.MaFederation.mappers.UserMapper;

import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; // mapper to convert User → DTO

    public List<ResponseUserDTO> getAllMods() {
        List<User> mods = userRepository.findByRole(RoleName.ADMIN);

        List<ResponseUserDTO> dtoList = mods.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());

        // Reverse the list
        Collections.reverse(dtoList);

        return dtoList;
    }

}




