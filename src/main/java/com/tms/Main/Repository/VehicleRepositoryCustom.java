package com.tms.Main.Repository;

import com.tms.Main.Model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VehicleRepositoryCustom {
    Page<Map<String, Object>> findProjectedVehicles(Long companyId, Long ownerLedgerId,
                                                    Set<String> fields, Pageable pageable);
}