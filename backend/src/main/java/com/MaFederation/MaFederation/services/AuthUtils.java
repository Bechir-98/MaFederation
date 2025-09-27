package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.model.User;
import com.MaFederation.MaFederation.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtils {

    private final UserRepository userrepository;

    public AuthUtils(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    // Make this NON-STATIC
    public String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return "anonymous";

        Optional<User> user = userrepository.findByEmail(auth.getName());
        return user.map(u -> u.getFirstName() + " " + u.getLastName()).orElse("anonymous");
    }
}
