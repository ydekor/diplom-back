package com.ydekor.diplomback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.model.user.SpaUser;
import com.ydekor.diplomback.repository.NoteRepository;
import com.ydekor.diplomback.service.auth.AuthenticationService;
import com.ydekor.diplomback.service.auth.UserService;
import com.ydekor.diplomback.web.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
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
        return noteRepository.findAllByUsersContains(authenticationService.getCurrentUser());
    }

    public NoteDto getById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        NoteDto noteDto = objectMapper.convertValue(note, NoteDto.class);
        noteDto.setUserEmails(note
                .getUsers()
                .stream()
                .map(SpaUser::getEmail)
                .collect(Collectors.toList())
        );

        return noteDto;
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
