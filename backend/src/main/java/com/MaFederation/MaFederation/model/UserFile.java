package com.MaFederation.MaFederation.model;

import com.MaFederation.MaFederation.enums.UserFileType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserFile extends Audit {

    
    @Lob
    private byte[] content;
    @Enumerated(EnumType.STRING)

    
    private UserFileType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
