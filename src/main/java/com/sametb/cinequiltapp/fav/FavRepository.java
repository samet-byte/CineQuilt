package com.sametb.cinequiltapp.fav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:47 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface FavRepository extends JpaRepository<Favourite, Long> {
    Favourite getByUserIdAndMetadataId(Integer userId, Integer metadataId);

    List<Favourite> getAllByUserId(Integer userId);

    @Transactional
    void deleteAllByMetadataId(Integer metadataId);
}

