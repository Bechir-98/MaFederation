    package com.MaFederation.MaFederation.model;

    import jakarta.persistence.*;
    import lombok.*;
    import java.util.List;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Staff extends ClubMember {

        private String specialty;

        @OneToMany
        @JoinTable(
            name = "staff_category",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
        )
        private List<Category> categories;
    }
