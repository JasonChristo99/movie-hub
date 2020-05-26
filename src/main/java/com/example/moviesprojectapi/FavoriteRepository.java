package com.example.moviesprojectapi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    List<Favorite> findByUserId(long userId);
    Favorite findByUserIdAndMovieId(long userId, String movieId);
    void deleteByUserIdAndMovieId(long userId, String movieId);
}
