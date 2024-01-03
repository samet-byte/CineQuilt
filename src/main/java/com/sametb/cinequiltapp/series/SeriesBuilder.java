package com.sametb.cinequiltapp.series;

import com.sametb.cinequiltapp.metadata.RelationType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 4:45 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class SeriesBuilder {
    static Series buildSeriesWithMetadataRequestAndSeriesRequest(
            @NotNull SeriesRequest seriesRequest
    ) {
        return Series.builder()
                .episodeNumber(seriesRequest.getEpisodeNumber())
                .seasonNumber(seriesRequest.getSeasonNumber())
                .id(seriesRequest.getId())
                .title(seriesRequest.getTitle())
                .director(Optional.ofNullable(seriesRequest.getDirector()).orElse("N/A"))
                .releaseYear(Optional.of(seriesRequest.getReleaseYear()).orElse(0))
                .duration(Optional.of(seriesRequest.getDuration()).orElse(0))
                .posterUrl(Optional.ofNullable(seriesRequest.getPosterUrl()).orElse("https://sametb.com/no-content.jpg"))
                .videoUrl(seriesRequest.getVideoUrl())
                .trailerUrl(seriesRequest.getTrailerUrl())
                .soundtrackUrl(seriesRequest.getSoundtrackUrl())
                .backgroundImageUrl(seriesRequest.getBackgroundImageUrl())
                .description(seriesRequest.getDescription())
                .genre(seriesRequest.getGenre())
                .type(RelationType.TV_SHOW)
                .build();
    }
}
