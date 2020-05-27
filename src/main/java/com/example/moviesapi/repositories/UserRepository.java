package com.example.moviesapi.repositories;

import com.example.moviesapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String encodedPassword);
}
