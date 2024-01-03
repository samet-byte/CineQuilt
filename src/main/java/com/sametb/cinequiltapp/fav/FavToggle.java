package com.sametb.cinequiltapp.fav;

import com.sametb.cinequiltapp.metadata.IMetadataService;
import com.sametb.cinequiltapp.user.IUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.sametb.cinequiltapp.fav.FavBuilder.buildFavWithRequest;

/**
 * @author Samet Bayat.
 * Date: 2.01.2024 7:41 PM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


public class FavToggle {

    @NotNull
    public static Map<String, Object> getStringObjectMapIsFavedResponse(
            @NotNull FavRequest favRequest,
            boolean changeState,
            @NotNull IFavService favouriteService,
            IUserService userService,
            IMetadataService metadataService
    ) {
        Favourite favourite = favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId());

        boolean shouldChangeState = false || changeState;
        boolean isFavedResult = false;

        if (favourite == null) {
            if (shouldChangeState) {
                favouriteService.saveFavourite(buildFavWithRequest(favRequest, userService, metadataService));
                isFavedResult = true;
            }
        } else {
            if (shouldChangeState) {
                favouriteService.deleteFavourite(favourite.getId());
            } else {
                isFavedResult = true;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("faved", isFavedResult);
        return response;
    }

}
