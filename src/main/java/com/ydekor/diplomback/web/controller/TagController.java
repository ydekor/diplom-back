package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.model.tag.Tag;
import com.ydekor.diplomback.service.TagService;
import com.ydekor.diplomback.web.dto.SimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @PutMapping
    public Tag updateRecord(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @DeleteMapping("/{id}")
    public SimpleResponseDTO deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new SimpleResponseDTO("tag id " + id + " deleted success");
    }

    @GetMapping
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag getOneTag(@PathVariable Long id) {
        return tagService.getById(id);
    }
}
