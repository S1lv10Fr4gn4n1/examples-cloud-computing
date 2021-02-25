package com.sfs.photoapp.api.users.data.repository;

import com.sfs.photoapp.api.users.data.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    // JPA will create the SELECT based on the method name
    // - findBy will create the SELECT
    // - Email is the WHERE filter
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);
}
