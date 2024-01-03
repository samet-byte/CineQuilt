package com.sametb.cinequiltapp.series;

import com.sametb.cinequiltapp._custom.BusinessHelper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:22 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Service
@RequiredArgsConstructor
public class SeriesService implements ISeriesService {
    private final SeriesRepository seriesRepository;

    @Override
    public Series save(Series series) {
        return seriesRepository.save(series);
    }

    @Override
    public Iterable<Series> findAll() {
        return seriesRepository.findAll();
    }

    @Override
    public Series findById(Integer id) {
        return seriesRepository.getSeriesById(id);
    }

    @Override
    public void deleteById(Integer id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        seriesRepository.deleteAll();
    }

    @Override
    public Series update(@NotNull Series nSeries) {

        Series cSeries = seriesRepository.getSeriesById(nSeries.getId());

        BeanUtils.copyProperties(nSeries, cSeries, BusinessHelper.getNullPropertyNames(cSeries));

        return seriesRepository.save(nSeries);
    }

    @Override
    public Series findBySeasonNumberAndEpisodeNumber(int seasonNumber, int episodeNumber) {
        return seriesRepository.findBySeasonNumberAndEpisodeNumber(seasonNumber, episodeNumber);
    }

    @Override
    public List<Series> findBySeasonNumber(int seasonNumber) {
        return seriesRepository.findBySeasonNumber(seasonNumber);
    }
}
