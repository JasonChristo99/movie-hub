package com.example.moviesapi.util;

import com.example.moviesapi.model.Favorite;
import com.example.moviesapi.model.User;
import com.example.moviesapi.repositories.FavoriteRepository;
import com.example.moviesapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    public DbInit(UserRepository userRepository, FavoriteRepository favoriteRepository) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void run(String... args) {
        // create a dummy users with a favorite movie
        User dummyUser1 = new User("u1@mail.com", PasswordEncryptor.toSHA1("u1"));
        this.userRepository.save(dummyUser1);
        User dummyUser2 = new User("u2@mail.com", PasswordEncryptor.toSHA1("u2"));
        this.userRepository.save(dummyUser2);
        // create favorites
        Favorite dummyFavorite1 = new Favorite(dummyUser2.getId(), "tt0111161");
        this.favoriteRepository.save(dummyFavorite1);
        Favorite dummyFavorite2 = new Favorite(dummyUser2.getId(), "tt0468569");
        this.favoriteRepository.save(dummyFavorite2);
        Favorite dummyFavorite3 = new Favorite(dummyUser2.getId(), "tt0050083");
        this.favoriteRepository.save(dummyFavorite3);
        Favorite dummyFavorite4 = new Favorite(dummyUser1.getId(), "tt0068646");
        this.favoriteRepository.save(dummyFavorite4);
        Favorite dummyFavorite5 = new Favorite(dummyUser1.getId(), "tt0108052");
        this.favoriteRepository.save(dummyFavorite5);

    }
}
