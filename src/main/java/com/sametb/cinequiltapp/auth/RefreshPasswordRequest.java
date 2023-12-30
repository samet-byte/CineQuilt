package com.sametb.cinequiltapp.auth;

import lombok.*;

/**
 * @author Samet Bayat.
 * Date: 29.12.2023 11:54 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshPasswordRequest {
    private String email;
}
