package com.sametb.cinequiltapp.episode;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sametb.cinequiltapp.episode.EpisodeBuilder.areSame;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:52 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Service
@RequiredArgsConstructor
public class EpisodeService implements IEpisodeService {

    private final EpisodeRepository episodeRepository;

    @Override
    public Episode save(Episode Episode) {
        return episodeRepository.save(Episode);
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }


    @Override
    public List<Episode> getAllEpisodesBySeriesId(Integer id) {
        return episodeRepository.findAllBySeriesId(id);
    }

    @Override
    public List<Episode> getBySeriesIdAndSeason(Integer metadataId, Integer season) {
        return episodeRepository.findAllBySeriesIdAndSeasonOrderByEpisode(metadataId, season);
    }

    @Override
    public void deleteEpisodeById(Integer id) {
        episodeRepository.deleteById(id);
    }

    @Override
    public boolean updateEpisode(Integer id, EpisodeRequest episodeRequest) {
        if (episodeRequest == null) return false;

        Episode uEpisode = episodeRepository.findById(id).orElseThrow();

        if (areSame(episodeRequest, uEpisode)) return false;

        BeanUtils.copyProperties(episodeRequest, uEpisode, "id", "series");

        episodeRepository.save(uEpisode);
        return true;
    }

    @Override
    public Episode findByEpisodeIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode){
        return episodeRepository.findBySeriesIdAndSeasonAndEpisode(metadataId, season, episode);
    }

    @Override
    public Optional<Episode> getEpisodeById(Integer id) {
        return episodeRepository.findById(id);
    }


    @Override
    public void deleteAllBySeriesId(Integer metadataId) {
        episodeRepository.deleteAllBySeriesId(metadataId);
    }
}
