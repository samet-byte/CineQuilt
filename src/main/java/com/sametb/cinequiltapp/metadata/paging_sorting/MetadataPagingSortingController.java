package com.sametb.cinequiltapp.metadata.paging_sorting;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.RelationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import static com.sametb.cinequiltapp.metadata.paging_sorting.SortMetadata.*;

/**
 * @author Samet Bayat.
 * Date: 18.12.2023 8:20 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


// sıralama ve sayfalama için
@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.metadatas-search}")
@CrossOrigin("http://localhost:3000")
public class MetadataPagingSortingController {

    private final MetadataPagingSortingService metadataPagingSortingService;

    @GetMapping("/list")
    public Page<Metadata> getMetadataList(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String type
    ) {
        Pageable pageable;
        try { //direction = Sort.Direction.fromString(sortDirection)
                    Sort sort = Sort.by(
                    Sort.Order.by(sortBy),
                    Sort.Order.by(SORT_BY_TITLE.getValue()),
                    Sort.Order.by(SORT_BY_DIRECTOR.getValue()),
                    Sort.Order.by(SORT_BY_RELEASE_YEAR.getValue()),
                    Sort.Order.by(SORT_BY_DURATION.getValue()),
                    Sort.Order.by(SORT_BY_GENRE.getValue()),
                    Sort.Order.by(SORT_BY_CREATE_DATE.getValue())
            );



                    pageable = PageRequest.of(pageNumber, pageSize, sort);
//            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sort));
            return metadataPagingSortingService.getAllMetadataByType(RelationType.valueOf(type.toUpperCase()), pageable);
        } catch (Exception e) { // IllegalArgumentException e
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        }
        return metadataPagingSortingService.getAllMetadata(pageable);
    }
}
