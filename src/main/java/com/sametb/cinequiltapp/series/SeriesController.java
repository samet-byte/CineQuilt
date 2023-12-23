package com.sametb.cinequiltapp.series;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.episode.IEpisodeService;
import com.sametb.cinequiltapp.fav.IFavService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Samet Bayat.
 * Date: 22.12.2023 12:24 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("${endpoint.series}")
@RequiredArgsConstructor
public class SeriesController {

    private final ISeriesService seriesService;

    private final IEpisodeService episodeService;

    private final IFavService favService;

    @GetMapping
    public Iterable<Series> getAllSeries() {
        return seriesService.findAll();
    }

    /*@GetMapping("/season/{seasonNumber}")
    public List<Series> getAllSeriesBySeasonNumber(@PathVariable int seasonNumber) {
        return seriesService.findBySeasonNumber(seasonNumber);
    }

    @GetMapping("/season/{seasonNumber}/{episodeNumber}")
    public Series getSeriesBySeasonNumberAndEpisodeNumber(@PathVariable int seasonNumber, @PathVariable int episodeNumber) {
        return seriesService.findBySeasonNumberAndEpisodeNumber(seasonNumber, episodeNumber);
    }*/

    @PostMapping
    public Series createSeries(@RequestBody SeriesRequest series) {
        return seriesService.save(SeriesBuilder.buildSeriesWithMetadataRequestAndSeriesRequest(series));
    }

    @PutMapping(value = "/{id}")
    public Series updateSeries(@NonNull @PathVariable Integer id, @NotNull @RequestBody Series series) {
        series.setId(id);
        return seriesService.update(series);
    }


    @DeleteMapping("/{id}")
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