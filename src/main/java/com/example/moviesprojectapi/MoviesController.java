package com.example.moviesprojectapi;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MoviesController {
    // String apiKey = "5b059748";
    String apiKey = "123d465";
    private String apiUrlBySearch = "http://www.omdbapi.com/?apikey=" + apiKey + "&s={term}&page={page}";
    private String apiUrlById = "http://www.omdbapi.com/?apikey=" + apiKey + "&i={id}&plot={plot}";


    @GetMapping("/movies/search") // ?term={term}&page={page}
    public Object getMovieBySearch(@RequestParam(name = "term") String term, @RequestParam(name = "page", required = false) String page) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlBySearch, Object.class, term, page);
    }

    @GetMapping("/movies") // ?id={id}&plot={plot}
    public Object getMovieByQuery(@RequestParam(name = "id") String id, @RequestParam(name = "plot", required = false) String plot) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlById, Object.class, id, plot);
    }


    @GetMapping("/movies/id/{id}")
    public Object getMovieById(@PathVariable(value = "id") String id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlById, Object.class, id, null);
    }
}
