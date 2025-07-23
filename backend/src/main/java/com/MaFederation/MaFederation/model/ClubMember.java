    package com.MaFederation.MaFederation.model;

    import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;   


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // or JOINED
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Player.class, name = "PLAYER"),
    @JsonSubTypes.Type(value = Staff.class, name = "STAFF"),
    @JsonSubTypes.Type(value = Administration.class, name = "ADMIN")
})
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClubMember extends User {
    private String role;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    // You must have the 'type' field visible to Jackson
    @Column(insertable = false, updatable = false)
    private String type;
}
