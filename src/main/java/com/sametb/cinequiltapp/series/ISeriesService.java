package com.sametb.cinequiltapp.series;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:25 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface ISeriesService {
    Series save(Series series);

    Iterable<Series> findAll();

    Series findById(Integer id);

    void deleteById(Integer id);

    void deleteAll();

    Series update(Series series);

    /**
     * @deprecated Use {@link #findBySeasonNumber(int)} instead.
     */
    @Deprecated
    Series findBySeasonNumberAndEpisodeNumber(int seasonNumber, int episodeNumber);

    List<Series> findBySeasonNumber(int seasonNumber);
}
