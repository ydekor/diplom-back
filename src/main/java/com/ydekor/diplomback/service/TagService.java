package com.ydekor.diplomback.service;

import com.ydekor.diplomback.model.tag.Tag;
import com.ydekor.diplomback.repository.TagRepository;
import com.ydekor.diplomback.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final AuthenticationService authenticationService;

    public Tag create(Tag tag) {
        if (tag.getId() != null) {
            throw new RuntimeException("id is not allowed here");
        }

        tag.setUser(authenticationService.getCurrentUser());
        return tagRepository.save(tag);
    }

    public Tag update(Tag tag) {
        if (tag.getId() == null) {
            throw new RuntimeException("id is required");
        }
        return tagRepository.save(tag);
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> getAll() {
        return tagRepository.findAllByUser(authenticationService.getCurrentUser());
    }

    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }
}
