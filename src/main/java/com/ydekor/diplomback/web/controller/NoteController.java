package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.service.NoteService;
import com.ydekor.diplomback.web.dto.AddTagDto;
import com.ydekor.diplomback.web.dto.NoteDto;
import com.ydekor.diplomback.web.dto.ShareNoteDto;
import com.ydekor.diplomback.web.dto.SimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.create(note);
    }

    @PostMapping("/tag")
    public void addTag(@RequestBody AddTagDto addTagDto) {
        noteService.addTag(addTagDto.getNoteId(), addTagDto.getTagId());
    }

    @DeleteMapping("/tag")
    public void deleteTag(@RequestBody AddTagDto addTagDto) {
        noteService.deleteTag(addTagDto.getNoteId(), addTagDto.getTagId());
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
    public NoteDto getOneNote(@PathVariable Long id) {
        return noteService.noteToDto(noteService.getById(id));
    }


    @PutMapping("/share")
    public Note shareNote(@RequestBody ShareNoteDto shareNoteDto) {
        return noteService.shareNote(shareNoteDto.getNoteId(), shareNoteDto.getUserEmail());
    }

}
