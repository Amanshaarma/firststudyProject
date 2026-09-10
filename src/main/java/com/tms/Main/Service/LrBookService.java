package com.tms.Main.Service;

import com.tms.Main.Model.Trip;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LrBookService {

    @Transactional
    boolean updateLrBookTripId(Trip trip, List<Long> lrBookIds);
}
