package com.sametb.cinequiltapp.metadata;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 16.12.2023 1:35 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class MetadataBuilder {

    public static Metadata buildMetadataWithRequest(@NotNull MetadataRequest request) {
        RelationType relationType = (request.getType() != null) ? request.getType() : RelationType.MOVIE;

        return Metadata.builder()
                .id(request.getId())
                .title(request.getTitle())
                .director(Optional.ofNullable(request.getDirector()).orElse("N/A"))
                .releaseYear(Optional.of(request.getReleaseYear()).orElse(0))
                .duration(Optional.of(request.getDuration()).orElse(0))
                .posterUrl(Optional.ofNullable(request.getPosterUrl()).orElse("https://sametb.com/no-content.jpg"))
                .videoUrl(request.getVideoUrl())
                .trailerUrl(request.getTrailerUrl())
                .soundtrackUrl(request.getSoundtrackUrl())
                .backgroundImageUrl(request.getBackgroundImageUrl())
                .description(request.getDescription())
                .genre(request.getGenre())
                .type(relationType)
                .build();
    }
}
