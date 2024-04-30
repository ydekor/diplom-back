package com.ydekor.diplomback.service.auth;

import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public Note create(Note note) {
        if (note.getId() != null) {
            throw new RuntimeException("id is not allowed here");
        }
        return noteRepository.save(note);
    }

    public Note update(Note note) {
        if (note.getId() == null) {
            throw new RuntimeException("id is required");
        }
        return noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    public Note getById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }
}
