package com.ydekor.diplomback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.model.tag.Tag;
import com.ydekor.diplomback.model.user.SpaUser;
import com.ydekor.diplomback.repository.NoteRepository;
import com.ydekor.diplomback.service.auth.AuthenticationService;
import com.ydekor.diplomback.service.auth.UserService;
import com.ydekor.diplomback.web.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final TagService tagService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public Note create(Note note) {
        if (note.getId() != null) {
            throw new RuntimeException("id is not allowed here");
        }
        if (note.getUsers() == null) {
            note.setUsers(new ArrayList<>());
        }
        note.getUsers().add(authenticationService.getCurrentUser());

        return noteRepository.save(note);
    }

    public Note update(Note frontNote) {
        if (frontNote.getId() == null) {
            throw new RuntimeException("id is required");
        }

        Note updatedNote = getById(frontNote.getId());

        updatedNote.setTitle(frontNote.getTitle());
        updatedNote.setNoteType(frontNote.getNoteType());
        updatedNote.setText(frontNote.getText());
        updatedNote.setNotificationDate(frontNote.getNotificationDate());
        updatedNote.setBackgroundColor(frontNote.getBackgroundColor());

        return noteRepository.save(updatedNote);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> getAll() {
        return noteRepository.findAllByUsersContains(authenticationService.getCurrentUser());
    }

    public NoteDto noteToDto(Note note) {
        NoteDto noteDto = objectMapper.convertValue(note, NoteDto.class);
        noteDto.setUserEmails(note
                .getUsers()
                .stream()
                .map(SpaUser::getEmail)
                .collect(Collectors.toList())
        );
        noteDto.setTags(note.getTags());
        return noteDto;
    }

    public Note getById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    public void addTag(Long noteId, Long tagId) {
        //найти note
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

        //найти tag
        Tag tag = tagService.getById(tagId);

        //добавить в note => tag
        if (note.getTags() == null) {
            note.setTags(new ArrayList<>());
        }
        note.getTags().add(tag);

        //сохранить изменения
        noteRepository.save(note);
    }

    public void deleteTag(Long noteId, Long tagId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

        Tag tag = tagService.getById(tagId);

        note.getTags().remove(tag);

        noteRepository.save(note);
    }

    public Note shareNote(Long noteId, String userEmail) {
        // check access user to note
        Note note = noteRepository
                .findById(noteId)
                .filter(e -> e.getUsers().contains(authenticationService.getCurrentUser()))
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // find user
        SpaUser newUser = userService.getUserByEmail(userEmail);

        // update note (add user)
        note.getUsers().add(newUser);

        return noteRepository.save(note);
    }

}
