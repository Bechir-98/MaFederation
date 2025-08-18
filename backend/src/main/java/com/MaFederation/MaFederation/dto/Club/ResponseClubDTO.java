package com.MaFederation.MaFederation.dto.Club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.MaFederation.MaFederation.enums.ValidationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClubDTO {

    private Integer id;
    private String name;
    private String location;
    private Integer foundedYear;
    private String contactEmail;
    private String contactPhone;
    private String bankAccount;
    private String bankName;
    private String website;       
    private byte[] logo;
    private List<Integer> categoryIds;
    private List<Integer> memberIds;
    private List<Integer> fileIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private ValidationStatus validated;
    private String validatedBy;    
    private LocalDateTime validationDate; 
    private String rejectionReason;  
    
}
