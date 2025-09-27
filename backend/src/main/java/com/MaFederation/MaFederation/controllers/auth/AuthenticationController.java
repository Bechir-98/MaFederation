package com.MaFederation.MaFederation.controllers.auth;


import com.MaFederation.MaFederation.services.ClubServices;
import com.MaFederation.MaFederation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register/admin")
    public void register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
    }
    @PostMapping("/register/ClubAdmin")
    public void registerClubAdmin(@RequestBody ClubRegisterRequest request)
    {
        service.registerClubAdmin(request);
    }


    @PostMapping("auth/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("auth/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
