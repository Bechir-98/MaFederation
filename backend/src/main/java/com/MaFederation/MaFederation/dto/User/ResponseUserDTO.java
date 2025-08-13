package com.MaFederation.MaFederation.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {

    private Integer id;
    private byte[] profilePicture;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationalID;
    private String nationality;
    private String type;
    private List<Integer> fileIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean validated;
    private String validatedBy;
    private LocalDateTime validationDate;
    private String rejectionReason;



}
    

