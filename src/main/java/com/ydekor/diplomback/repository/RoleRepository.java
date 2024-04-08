package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.user.SpaRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<SpaRole, Long> {
    List<SpaRole> findByIsDefaultIsTrue();

    Optional<SpaRole> findByName(String name);
}