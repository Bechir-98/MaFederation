    package com.MaFederation.MaFederation.dto.Club;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class PostClubDTO {

        private String name;
        private String location;
        private Integer foundedYear;
        private String contactEmail;
        private String contactPhone;
        private String bankAccount;
        private String bankName;
        private byte[] logo; 
        private List<Integer> categoryIds;
        
    }
