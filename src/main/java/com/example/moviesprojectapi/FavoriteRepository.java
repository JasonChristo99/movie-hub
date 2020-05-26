package com.example.moviesprojectapi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    List<Favorite> findByUserId(long userId);
}
