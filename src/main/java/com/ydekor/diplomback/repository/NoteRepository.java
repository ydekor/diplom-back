package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.note.Note;
import com.ydekor.diplomback.model.user.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUsersContains(SpaUser user);

}
