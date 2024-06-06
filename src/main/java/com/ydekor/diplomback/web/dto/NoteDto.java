package com.ydekor.diplomback.web.dto;


import com.ydekor.diplomback.model.note.NoteType;
import com.ydekor.diplomback.model.tag.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NoteDto {

    private Long id;
    private String title;
    private NoteType noteType;
    private String text;
    private LocalDateTime notificationDate;
    private String backgroundColor;
    private List<String> userEmails = new ArrayList<>();
    private List<Tag> tags;
}
