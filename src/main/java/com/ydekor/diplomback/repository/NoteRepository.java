package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
