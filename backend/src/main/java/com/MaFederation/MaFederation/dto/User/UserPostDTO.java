package com.MaFederation.MaFederation.dto.User;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO {
    private byte[] profilePicture; // Assuming profile picture is stored as byte array
    private String passwordHash;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationalID;
    private String nationality;

}
