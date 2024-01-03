package com.sametb.cinequiltapp.metadata;

import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public interface IMetadataService {

    boolean isMetadataAlreadyExists(String title);

    Metadata save(MetadataRequest request);

    List<Metadata> findAll();

    List<Metadata> findAllBy(
            @Nullable String col,
            @Nullable String val,
            @Nullable String by,
            @Nullable String order
    );

    void deleteMetadata(Integer id);

    Metadata findById(Integer id);

    Metadata findByTitle(String title);

    List<Metadata> findByTitleContaining(String title);

    List<Metadata> findByDirectorContaining(String director);

    List<Metadata> findByReleaseYear(int releaseYear);

    List<Metadata> findByDuration(int duration);

    List<Metadata> findByTitleContainingOrDirectorContaining(String query);

    Optional<Metadata> updateMetadataById(Integer id, Metadata updatedMetadata);
}
