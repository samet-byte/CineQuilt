package com.sametb.cinequiltapp.fav;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:46 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavRequest {
    private Integer metadataId;
    private Integer userId;
}
