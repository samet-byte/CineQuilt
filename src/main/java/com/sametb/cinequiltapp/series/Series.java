package com.sametb.cinequiltapp.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.episode.Episode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;


/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:18 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "series")
public class Series extends Metadata {

    @Column(name = "season_number")
    private int seasonNumber;

    @Column(name = "episode_number")
    private int episodeNumber;


    @JsonIgnore
    @OneToMany(
            mappedBy = "series",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            })
    private Set<Episode> Episodes;
}
