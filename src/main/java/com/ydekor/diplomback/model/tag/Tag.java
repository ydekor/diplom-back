package com.ydekor.diplomback.model.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"tag_name", "user_id"})})
@Entity
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "note_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"note_id", "tag_id"})}
    )
    private List<Note> notes;

    @JsonIgnore
    @ManyToOne
    private SpaUser user;
}
