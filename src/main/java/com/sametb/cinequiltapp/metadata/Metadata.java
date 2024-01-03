package com.sametb.cinequiltapp.metadata;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sametb.cinequiltapp.fav.Favourite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "metadatas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Metadata {

    // todo: UUID, Long to Integer -> GCloud consuming behaviour
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "metadata_id")
    private Integer id;

    @Column(name = "title", nullable = false) // length = 255 default
    @JoinColumn(name = "metadata_title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "duration")
    private int duration;

    @Column(name = "genre")
    private String genre;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "trailer_url", nullable = true)
    private String trailerUrl;

    @Column(name = "soundtrack_url", nullable = true)
    private String soundtrackUrl;

    @Column(name = "background_image_url ", nullable = true)
    private String backgroundImageUrl;


    // for logging, sorting and possible lawsuits :|
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    // ENUM 1-> Series, 2-> Movie
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = true)
    private RelationType type;

    @JsonIgnore
    @OneToMany(
            mappedBy = "metadata",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            })
    private Set<Favourite> favourites;
}
