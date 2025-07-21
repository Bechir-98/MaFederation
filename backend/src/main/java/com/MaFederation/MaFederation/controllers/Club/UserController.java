package com.MaFederation.MaFederation.controllers.Club;

import com.MaFederation.MaFederation.dto.User.UserDTO;
import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin( "http://localhost:4200/")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserDtoById(id);
        return ResponseEntity.ok(user);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDto) {
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    // // Update existing user
    // @PutMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDto) {
    //     userDto.setUserId(id); // ensure the ID matches path
    //     User updatedUser = userService.updateUser(userDto);
    //     return ResponseEntity.ok(updatedUser);
    // }

    // Delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
