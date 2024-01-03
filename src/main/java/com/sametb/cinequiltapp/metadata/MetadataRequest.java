package com.sametb.cinequiltapp.metadata;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class MetadataRequest {
    private Integer id;
    private String title;
    private String director;
    private int releaseYear;
    private int duration;
    private String genre;
    private String description;
    private String posterUrl;
    private String videoUrl;
    private String trailerUrl;
    private String soundtrackUrl;
    private String backgroundImageUrl;
    private RelationType type;
    private Integer season;
    private Integer episode;
}
