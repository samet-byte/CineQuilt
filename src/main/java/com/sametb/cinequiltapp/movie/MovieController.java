package com.sametb.cinequiltapp.movie;

/**
 * @author Samet Bayat.
 * Date: 2.01.2024 8:39 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.MetadataBuilder;
import com.sametb.cinequiltapp.metadata.MetadataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.sametb.cinequiltapp.metadata.MetadataBuilder.buildMetadataWithRequest;

@RestController
@RequestMapping("${endpoint.movies}")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    private final IMovieService movieService;

    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Iterable<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public Movie saveMovie(@RequestBody MetadataRequest metadataRequest) {
        Metadata metadata = buildMetadataWithRequest(metadataRequest);
        return movieService.saveMovie((Movie) metadata);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
    }
}

