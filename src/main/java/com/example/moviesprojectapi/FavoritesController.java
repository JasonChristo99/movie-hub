package com.example.moviesprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FavoritesController {
    @Autowired
    private final FavoriteRepository favoriteRepository;

    public FavoritesController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @PostMapping(value = "/favorite", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpStatus> favoriteMovie(@RequestParam(value = "movieId") String movieId,
                                                    HttpServletResponse httpResponse) {
        if (SessionDetails.getActiveUserId() == -1) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
        }
        System.out.println("Favorite request: user " + SessionDetails.getActiveUserId() + " for " + movieId);
        Favorite favorite = new Favorite(SessionDetails.getActiveUserId(), movieId);
        favoriteRepository.save(favorite);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/favorites/all")
    public Iterable<Favorite> getFavorites() {
        return this.favoriteRepository.findAll();
    }

    @GetMapping(value = "/favorites/{id}")
    public List<Favorite> getFavorites(@PathVariable(value = "id") String id) {
        return this.favoriteRepository.findByUserId(Long.parseLong(id));
    }
}