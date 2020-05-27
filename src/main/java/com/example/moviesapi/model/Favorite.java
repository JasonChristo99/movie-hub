package com.example.moviesapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(UserMovieId.class)
public class Favorite {
    @Id
    @Column(nullable = false)
    private long userId;

    @Id
    @Column(nullable = false)
    private String movieId;

    public Favorite() {
    }

    public Favorite(long userId, String movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return "Favorite{" + "userId=" + userId + ", movieId=" + movieId + '}';
    }
}

