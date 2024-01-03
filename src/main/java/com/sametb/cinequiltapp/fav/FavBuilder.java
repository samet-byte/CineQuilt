package com.sametb.cinequiltapp.fav;

import com.sametb.cinequiltapp.metadata.IMetadataService;
import com.sametb.cinequiltapp.metadata.MetadataService;
import com.sametb.cinequiltapp.user.IUserService;
import com.sametb.cinequiltapp.user.UserService;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 16.12.2023 2:19 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


public class FavBuilder {
    public static FavResponse fromFavorite(@NotNull Favourite favourite) {
        return FavResponse.builder()
                .userId(favourite.getUser().getId())
                .metadataId(favourite.getMetadata().getId())
                .title(favourite.getMetadata().getTitle())
                .posterUrl(favourite.getMetadata().getPosterUrl())
                .type(favourite.getMetadata().getType())
                .releaseYear(favourite.getMetadata().getReleaseYear())
                .build();
    }

    @NotNull
    public static List<FavResponse> fromFavourites(@NotNull List<Favourite> favourites) {
        List<FavResponse> favResponses = new ArrayList<>();
        for (Favourite favourite : favourites) {
            favResponses.add(fromFavorite(favourite));
        }
        return favResponses;
    }

    static Favourite buildFavWithRequest(
            @NotNull FavRequest favRequest,
            @NonNull IUserService userService,
            @NonNull IMetadataService metadataService
            ) {
        return Favourite.builder()
                .user(userService.findByIDNonOptional(favRequest.getUserId()))
                .metadata(metadataService.findById(favRequest.getMetadataId()))
                .createTime(LocalDateTime.now())
                .build();
    }
}
