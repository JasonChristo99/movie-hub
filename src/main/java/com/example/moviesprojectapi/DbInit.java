package com.example.moviesprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
    public void run(String... args) throws Exception {
        // create a dummy user with a favorite movie
        User dummyUser = new User("admin", PasswordEncryptor.toSHA1("123"));
        this.userRepository.save(dummyUser);
        Favorite dummyFavorite = new Favorite(dummyUser.getId(), "tt1285016");
        this.favoriteRepository.save(dummyFavorite);
    }
}
