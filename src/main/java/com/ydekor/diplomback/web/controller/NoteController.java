package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.service.auth.NoteService;
import com.ydekor.diplomback.web.dto.SimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.create(note);
    }

    @PutMapping
    public Note updateRecord(@RequestBody Note note) {
        return noteService.update(note);
    }

    @DeleteMapping("/{id}")
    public SimpleResponseDTO deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return new SimpleResponseDTO("note id " + id + " deleted success");
    }

    @GetMapping
    public List<Note> getAll() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getOneNote(@PathVariable Long id) {
        return noteService.getById(id);
    }
}
