package com.sametb.cinequiltapp.metadata;

import com.sametb.cinequiltapp._custom.BusinessHelper;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.exception.MetadataAlreadyExistsException;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.sametb.cinequiltapp.metadata.MetadataBuilder.buildMetadataWithRequest;


@Service
@RequiredArgsConstructor
public class MetadataService implements IMetadataService {

    private final MetadataRepository repository;

    @Override
    public boolean isMetadataAlreadyExists(String title) {
        return repository.findByTitleIgnoreCase(title).isPresent();
    }

    @Override
    public Metadata save(@NotNull MetadataRequest request) {

        if (request.getTitle() != null && isMetadataAlreadyExists(request.getTitle())) {
            throw new MetadataAlreadyExistsException(request.getTitle() + " already exists");
        } else {
            var metadata = buildMetadataWithRequest(request);
            repository.save(metadata);
            return metadata;
        }
    }

    @Override
    public List<Metadata> findAll() {
        return repository.findAll();
    }

    @Deprecated
    @Override
    public List<Metadata> findAllBy(
            @Nullable String col,
            @Nullable String val,
            @Nullable String by,
            @Nullable String order
    ) {
        try {
            return repository.findAllByColumnAndValue(col, val, by, order);
        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(e.getMessage());
            return repository.findAll();

        }
    }

    @Override
    @Transactional
    public void deleteMetadata(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(e.getMessage());
        }
    }

    @Override
    public Metadata findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this ID!"));
    }

    @Override
    public Metadata findByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this title!"));
    }

    @Override
    public List<Metadata> findByTitleContaining(String title) {
        return repository.findByTitleIgnoreCaseContaining(title);
    }

    @Override
    public List<Metadata> findByDirectorContaining(String director) {
        return repository.findByDirectorIgnoreCaseContaining(director);
    }

    @Override
    public List<Metadata> findByReleaseYear(int releaseYear) {
        return repository.findByReleaseYear(releaseYear);
    }

    @Override
    public List<Metadata> findByDuration(int duration) {
        return repository.findByDuration(duration);
    }


    @Override
    public List<Metadata> findByTitleContainingOrDirectorContaining(String query) {
        return repository.findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(query, query);
    }

    @Override
    public Optional<Metadata> updateMetadataById(Integer id, Metadata updatedMetadata) {
        if (updatedMetadata == null || id == null || repository.findById(id).isEmpty()) {
            return Optional.empty();
        }

        Metadata uMetadata = repository.findById(id).orElseThrow();

        // Use BeanUtils to copy non-null properties from updatedMetadata to uMetadata
        BeanUtils.copyProperties(updatedMetadata, uMetadata, BusinessHelper.getNullPropertyNames(updatedMetadata));

        // Check if any changes were made before saving
        boolean changesMade = !uMetadata.equals(updatedMetadata);

        if (changesMade) {
            // Save the updated entity back to the database
            return Optional.of(repository.save(uMetadata));
        } else {
            // No changes, return the existing entity
            return Optional.of(uMetadata);
        }
    }

}