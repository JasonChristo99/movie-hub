package com.example.moviesprojectapi;

import javax.persistence.*;

@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String movieId;

    public Favorite() {
    }

    public Favorite(long userId, String movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }
}

