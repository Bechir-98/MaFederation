package com.MaFederation.MaFederation.controllers;
import com.MaFederation.MaFederation.services.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor    
@CrossOrigin("http://localhost:4200")
public class UserSelectionController {

    private final UserService userService;

    @PostMapping("/select")
    public ResponseEntity<Void> selectUser(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer userId = body.get("userId");
        if (userId == null) return ResponseEntity.badRequest().build();

        session.setAttribute("selectedUserId", userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getSelectedUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("selectedUserId");
        if (userId == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(userService.getUserDtoById(userId));
    }
}
