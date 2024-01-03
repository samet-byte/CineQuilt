package com.sametb.cinequiltapp.movie;

import java.util.List;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 2.01.2024 8:34 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface IMovieService {
    Iterable<Movie> getAllMovies();
    Movie getMovieById(Integer id);
    Movie saveMovie(Movie movie);
    void deleteMovie(Integer id);
}
