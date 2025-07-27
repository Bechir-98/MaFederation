    package com.MaFederation.MaFederation.model;

    import jakarta.persistence.*;
    import lombok.*;
    import java.util.List;

    @Entity
    @DiscriminatorValue("STAFF")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Staff extends ClubMember {

        private String specialty;

    

    }
