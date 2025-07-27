package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.services.UserService;
import org.springframework.web.bind.annotation.*;




@RestController
@CrossOrigin( "http://localhost:4200/")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    

    
    }
    






    

