package com.sametb.cinequiltapp.series;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.episode.IEpisodeService;
import com.sametb.cinequiltapp.fav.IFavService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:24 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("${endpoint.series}")
public class SeriesController {

    private final ISeriesService seriesService;
    private final IEpisodeService episodeService;
    private final IFavService favService;

    @GetMapping
    public Iterable<Series> getAllSeries() {
        return seriesService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('manager:create')")
    public Series createSeries(@RequestBody SeriesRequest series) {
        return seriesService.save(SeriesBuilder.buildSeriesWithMetadataRequestAndSeriesRequest(series));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('manager:update')")
    public Series updateSeries(@NonNull @PathVariable Integer id, @NotNull @RequestBody Series series) {
        series.setId(id);
        return seriesService.update(series);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSeries(@NonNull @PathVariable Integer id){
        try {
            favService.deleteFavouriteByMetadataId(id);
            episodeService.deleteAllBySeriesId(id);
            seriesService.deleteById(id);
            return ResponseEntity.accepted().build();

        }catch (Exception e){
            SamTextFormat.Companion.errorMessage(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
