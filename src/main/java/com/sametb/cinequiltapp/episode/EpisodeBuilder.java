package com.sametb.cinequiltapp.episode;

import com.sametb.cinequiltapp.series.Series;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Samet Bayat.
 * Date: 30.12.2023 12:55 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class EpisodeBuilder {
    public static Episode fromTVShowRequest(@NotNull EpisodeRequest episodeRequest, Series series) {
        return Episode.builder()
                .series(series)
                .season(episodeRequest.getSeason())
                .episode(episodeRequest.getEpisode())
                .title(episodeRequest.getTitle())
                .videoUrl(episodeRequest.getVideoUrl())
                .description(episodeRequest.getDescription())
                .build();
    }


    public static boolean areSame(EpisodeRequest request, Episode episode) {
        if (request == null || episode == null) { return true; }
        boolean titlesEqual = Objects.equals(request.getTitle(), episode.getTitle());
        boolean descriptionsEqual = Objects.equals(request.getDescription(), episode.getDescription());
        boolean seasonsEqual = Objects.equals(request.getSeason(), episode.getSeason());
        boolean episodesEqual = Objects.equals(request.getEpisode(), episode.getEpisode());
        boolean videoUrlsEqual = Objects.equals(request.getVideoUrl(), episode.getVideoUrl());

        return titlesEqual && descriptionsEqual && seasonsEqual && episodesEqual && videoUrlsEqual;
    }
}
