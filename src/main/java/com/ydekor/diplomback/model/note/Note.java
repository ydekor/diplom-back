package com.ydekor.diplomback.model.note;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}
