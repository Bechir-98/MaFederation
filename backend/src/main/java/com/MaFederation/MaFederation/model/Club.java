package com.MaFederation.MaFederation.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer clubId;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;

    private Integer foundedYear;

    private String contactEmail;

    private String contactPhone;

    private String bankAccount;

    private String bankName;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> members = new ArrayList<>();

@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
    name = "club_categories",
    joinColumns = @JoinColumn(name = "club_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private List<Category> categories = new ArrayList<>();


    @Lob
    private byte[] logo;

   @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
   
    private List<ClubFile> files = new ArrayList<>();
}
