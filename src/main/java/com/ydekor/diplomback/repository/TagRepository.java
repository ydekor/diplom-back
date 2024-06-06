package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.tag.Tag;
import com.ydekor.diplomback.model.user.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository <Tag, Long> {
    List<Tag> findAllByUser(SpaUser user);

}
