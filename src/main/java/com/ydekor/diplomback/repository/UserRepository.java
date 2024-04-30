package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.user.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SpaUser, Long> {

    Optional<SpaUser> findByLogin(@Param("login") String login);

    Optional<SpaUser> findByEmail(@Param("email") String email);

}
