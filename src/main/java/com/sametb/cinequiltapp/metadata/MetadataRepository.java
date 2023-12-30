package com.sametb.cinequiltapp.metadata;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata, Integer> {

    @NotNull
    Optional<Metadata> findById(@NotNull Integer id);

    void deleteById(@NonNull Integer id);

    Optional<Metadata> findByTitleIgnoreCase(String title);

    List<Metadata> findByTitleIgnoreCaseContaining(String substring);

    List<Metadata> findByDirectorIgnoreCaseContaining(String substring);

    List<Metadata> findByReleaseYear(int releaseYear);

    List<Metadata> findByDuration(int duration);

    List<Metadata> findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(String title, String director);

    Optional<Metadata> findByTitle(String title);

    @Deprecated
    @Query(value = """
    select * from 
    get_by_column_and_value_sort_by_column_and_order
    (:col, :val, :by, :order)
    """, nativeQuery = true)
    List<Metadata> findAllByColumnAndValue(
            @Param("col") String col,
            @Param("val") String val,
            @Param("by") String by,
            @Param("order") String order
    );
}
