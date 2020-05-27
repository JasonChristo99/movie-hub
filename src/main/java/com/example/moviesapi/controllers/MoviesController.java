package com.example.moviesapi.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
public class MoviesController {
    // String apiKey = "5b059748";
    String apiKey = "123d465";
    private String apiUrlBySearch = "http://www.omdbapi.com/?apikey=" + apiKey + "&s={term}&page={page}";
    private String apiUrlByIdAndPlot = "http://www.omdbapi.com/?apikey=" + apiKey + "&i={id}&plot={plot}";

    @GetMapping("/movies") // ?term={term}&page={page}
    public Object getMoviesBySearch(@RequestParam(name = "term") String term, @RequestParam(name = "page", required = false) String page) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlBySearch, Object.class, term, page);
    }

    @GetMapping({"/movies/{id}", "/movies/{id}/{plot}"})
    public Object getMovieTest(@PathVariable(value = "id") String id,
                               @PathVariable(value = "plot", required = false) String plot) {
        return new RestTemplate().getForObject(apiUrlByIdAndPlot, Object.class, id, plot);
    }
}
