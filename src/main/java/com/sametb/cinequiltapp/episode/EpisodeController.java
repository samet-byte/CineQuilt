package com.sametb.cinequiltapp.episode;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.series.ISeriesService;
import com.sametb.cinequiltapp.series.Series;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.sametb.cinequiltapp.episode.EpisodeBuilder.fromTVShowRequest;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:52 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("${endpoint.episode}")
@RequiredArgsConstructor
public class EpisodeController {

    private final IEpisodeService episodeService;
    private final ISeriesService seriesService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public EpisodeResponse saveTVShow(@NotNull @RequestBody EpisodeRequest episodeRequest) {
        Series cSeries = seriesService.findById(episodeRequest.getMetadataId());
        SamTextFormat.Companion.create(cSeries.toString()).cyan().print();
        Episode episode = fromTVShowRequest(episodeRequest, cSeries);
        Episode savedEpisode = episodeService.save(episode);
        return EpisodeResponse.fromTVShow(savedEpisode);
    }

    @GetMapping("demo")
    public ResponseEntity<?> demo(@NotNull @RequestBody EpisodeRequest episodeRequest){
        Series cSeries = seriesService.findById(episodeRequest.getMetadataId());

        return ResponseEntity.ok(
                cSeries.toString()
        );
    }

    @GetMapping
    public List<Episode> getAllTVShows()  {
        return episodeService.getAllEpisodes();
    }


    @GetMapping("/{id}")
    public List<Episode> getAllTVShowsByMetadataId(@PathVariable Integer id)  {
        return episodeService.getAllEpisodesBySeriesId(id);
    }


    @GetMapping("/episode/{id}")
    public Optional<Episode> getTVShowById(@PathVariable Integer id)  {
        return episodeService.getEpisodeById(id);
    }

    @Deprecated
    @GetMapping("/{metadataId}/{index}")
    public Episode getTVShowByMetadataIdAndTVShowId(@PathVariable Integer metadataId, @PathVariable Integer index)  {
        return episodeService.getAllEpisodesBySeriesId(metadataId).get(index);
    }

    @GetMapping("season/{metadataId}/{season}")
    public List<Episode> getTVShowByMetadataIdAndSeason(@PathVariable Integer metadataId, @PathVariable Integer season)  {
        return episodeService.getBySeriesIdAndSeason(metadataId, season);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpisode(@PathVariable Integer id) {
        try{
            episodeService.deleteEpisodeById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e){
            SamTextFormat.Companion.create(e.getMessage()).red().print();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpisode(@NonNull @PathVariable Integer id, @RequestBody EpisodeRequest episodeRequest){
        try{
            boolean isUpdated = episodeService.updateEpisode(id, episodeRequest);

            if (isUpdated)
                return ResponseEntity.ok().build();
        } catch (Exception e){
            SamTextFormat.Companion.errorMessage(e.getMessage());
        }
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("{metadataId}/{season}/{episode}")
    public Episode getTVShowByMetadataIdAndSeasonAndEpisode(
            @PathVariable Integer metadataId,
            @PathVariable Integer season,
            @PathVariable Integer episode
    )  {
        return episodeService.findByEpisodeIdAndSeasonAndEpisode(metadataId, season, episode);
    }
}
