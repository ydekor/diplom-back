package com.ydekor.diplomback.model.note;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ydekor.diplomback.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private NoteType noteType;
    private String text;
    private LocalDateTime notificationDate;
    private String backgroundColor;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "note_user",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"note_id", "user_id"})}
    )
    private List<SpaUser> users;

}
