package com.sametb.cinequiltapp.series;

import com.sametb.cinequiltapp.metadata.MetadataRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 4:47 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class SeriesRequest extends MetadataRequest {
    private int episodeNumber;
    private int seasonNumber;
}
