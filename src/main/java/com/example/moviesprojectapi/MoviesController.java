package com.example.moviesprojectapi;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MoviesController {
    //    private String apiUrl = "http://www.omdbapi.com/?apikey=5b059748&";
    private String apiUrlBySearch = "http://www.omdbapi.com/?apikey=5b059748&s={term}";
    private String apiUrlById = "http://www.omdbapi.com/?apikey=5b059748&i={id}&plot={plot}";


    @GetMapping("/movies/{term}")
    public Object getMovieBySearch(@PathVariable(value = "term") String term) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlBySearch, Object.class, term);
    }

    @GetMapping("/movies/id/{id}&{plot}")
    public Object getMovieById(@PathVariable(value = "id") String id, @PathVariable(value = "plot", required = false) String plot) {
        String finPlot;
        if (plot == null) {
            finPlot = "short";
        } else {
            finPlot = plot;
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrlById, Object.class, id, finPlot);
    }
}
