package com.sametb.cinequiltapp.series;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:21 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface SeriesRepository extends JpaRepository<Series, Integer> {
    Series findBySeasonNumberAndEpisodeNumber(int seasonNumber, int episodeNumber);

    List<Series> findBySeasonNumber(int seasonNumber);

    Series getSeriesById(Integer id);
}
