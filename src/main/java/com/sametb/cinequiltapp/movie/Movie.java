package com.sametb.cinequiltapp.movie;

import com.sametb.cinequiltapp.metadata.Metadata;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 8:31 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@RequiredArgsConstructor
@Table(name = "movies")
public class Movie extends Metadata {
}
