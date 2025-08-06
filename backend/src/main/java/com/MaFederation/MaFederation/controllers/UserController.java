package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
import com.MaFederation.MaFederation.dto.User.UserPostDTO;
import com.MaFederation.MaFederation.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@CrossOrigin( "http://localhost:4200/")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserPostDTO userPostDTO) {
        String result = userService.createUser(userPostDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUser(@PathVariable Integer id) {
        ResponseUserDTO userDto = userService.loadUser(id);
        if (userDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDto);
    }



    

    
    }
    






    

