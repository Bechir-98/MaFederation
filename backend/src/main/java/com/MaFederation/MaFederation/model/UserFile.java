package com.MaFederation.MaFederation.model;

import com.MaFederation.MaFederation.enums.FileType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private FileType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
