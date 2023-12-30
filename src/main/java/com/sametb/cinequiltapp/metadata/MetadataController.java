package com.sametb.cinequiltapp.metadata;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import com.sametb.cinequiltapp.fav.IFavService;
import com.sametb.cinequiltapp.episode.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sametb.cinequiltapp._custom.CustomFunsKt.decodeString;

// düzenleme,  ekleme
//crud

@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.metadatas}")
@CrossOrigin("http://localhost:3000")
public class MetadataController {

//    new MetadataService(repository);
    private final MetadataService service;
    private final IFavService favoritesService;
    private final EpisodeService episodeService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'manager:create')")
    public ResponseEntity<?> save(
            @RequestBody MetadataRequest request
    ) {
        Metadata savedMetadata = service.save(request);
        return ResponseEntity.accepted().body(savedMetadata); //.build();
    }


    @Deprecated
    @GetMapping
    public ResponseEntity<List<Metadata>> findAllMetadatasBy(
            @RequestParam(required = false) String col,
            @RequestParam(required = false) String val,
            @RequestParam(required = false) String by,
            @RequestParam(required = false) String order
    ) {
        if (by != null && order != null) {
            return ResponseEntity.ok(service.findAllBy(col, val, by, order));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metadata> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Metadata>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findByTitleContaining(title));
    }
    @GetMapping("/title/single/{title}")
    public ResponseEntity<Metadata> findByTitleSingle(@PathVariable String title) {
        try {
            String searchTitle = decodeString(title);
            return ResponseEntity.ok(service.findByTitle(searchTitle));
        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<List<Metadata>> findByDirector(@PathVariable String director) {
        return ResponseEntity.ok(service.findByDirectorContaining(director));
    }
    
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Metadata>> findByYear(@PathVariable int year) {
        return ResponseEntity.ok(service.findByReleaseYear(year));
    }
    
    @GetMapping("/duration/{duration}")
    public ResponseEntity<List<Metadata>> findByDuration(@PathVariable int duration) {
        return ResponseEntity.ok(service.findByDuration(duration));
    }
    
    @GetMapping("/search/{query}")
    public ResponseEntity<List<Metadata>> findByQuery(@PathVariable String query) {
        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContaining(query));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'manager:update')")
    public ResponseEntity<Metadata> updateMetadata(@RequestBody Metadata metadata, @PathVariable Integer id) {
        return ResponseEntity.ok(service.updateMetadataById(id, metadata)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this ID!")));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Metadata> deleteMetadata(
            @PathVariable Integer id
    ) {
        try{
            //todo: if series_delete episodes
            episodeService.deleteAllBySeriesId(id);
            favoritesService.deleteFavouriteByMetadataId(id);
            service.deleteMetadata(id);
            return ResponseEntity.accepted().build(); // .body(deletedMetadata); //.build();}
    } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}



