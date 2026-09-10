package com.tms.Main.Service.impl;

import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Model.LrBook;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.LrBookRepository;
import com.tms.Main.Service.LrBookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LrBookImpl implements LrBookService
{
    private final LrBookRepository lrBookRepository;


    @Transactional
    public boolean updateLrBookTripId(Trip trip, List<Long> lrBookIds) {

        if (trip == null || trip.getTripId() == null) {
            throw new IllegalArgumentException("Trip is required");
        }

        if (lrBookIds == null || lrBookIds.isEmpty()) {
            return true;
        }

        // Remove duplicate IDs
        List<Long> uniqueLrBookIds = lrBookIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // Find all LR books
        List<LrBook> lrBooks =
                lrBookRepository.findAllById(uniqueLrBookIds);

        // Make sure every requested LR was found
        if (lrBooks.size() != uniqueLrBookIds.size()) {

            Set<Long> foundIds = lrBooks.stream()
                    .map(LrBook::getLrId)
                    .collect(Collectors.toSet());

            List<Long> missingIds = uniqueLrBookIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ResourceNotFoundException(
                    "LR Book not found for IDs: " + missingIds
            );
        }

        // Update trip ID
        lrBooks.forEach(lrBook ->
                lrBook.setTrip(trip)
        );

        // Save all
        lrBookRepository.saveAll(lrBooks);

        return true;
    }
}
