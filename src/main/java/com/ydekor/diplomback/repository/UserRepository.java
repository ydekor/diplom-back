package com.ydekor.diplomback.repository;

import com.ydekor.diplomback.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
