package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserSelectionController {

    private final UserService userService;

    // Get a user directly by ID
    @GetMapping("/{userId}")
    public Object getUserById(@PathVariable Integer userId) {
        try {
            Object userDto =  userService.getUserDtoById(userId);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
