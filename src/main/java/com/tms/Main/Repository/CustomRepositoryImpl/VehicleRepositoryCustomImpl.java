package com.tms.Main.Repository.CustomRepositoryImpl;

import com.tms.Main.Model.Ledger;
import com.tms.Main.Model.Vehicle;
import com.tms.Main.Repository.VehicleRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class VehicleRepositoryCustomImpl implements VehicleRepositoryCustom {

    private static final int MAX_PAGE_SIZE = 200;

    private final EntityManager entityManager;

    public VehicleRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Map<String, Object>> findProjectedVehicles(Long companyId, Long ownerLedgerId,
                                                           Set<String> fields, Pageable pageable) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("At least one projected field is required");
        }
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must not exceed " + MAX_PAGE_SIZE);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // ---- Data query ----
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Vehicle> root = query.from(Vehicle.class);

        // ownerLedgerName is auto-bundled whenever ownerLedgerId is requested — not independently selectable
        Set<String> effectiveFields = new LinkedHashSet<>(fields);
        if (effectiveFields.contains("ownerLedgerId")) {
            effectiveFields.add("ownerLedgerName");
        }

        Join<Vehicle, Ledger> ledgerJoin = effectiveFields.contains("ownerLedgerName")
                ? root.join("ownerLedger", JoinType.LEFT) // owner_ledger_id is nullable — LEFT is mandatory here
                : null;

        List<Selection<?>> selections = effectiveFields.stream()
                .map(field -> mapFieldToSelection(root, ledgerJoin, field))
                .collect(Collectors.toList());
        query.multiselect(selections);
        query.where(buildPredicate(cb, root, companyId, ownerLedgerId));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<Tuple> tuples = typedQuery.getResultList();

        List<Map<String, Object>> content = tuples.stream()
                .map(tuple -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (String field : effectiveFields) {
                        row.put(field, tuple.get(field));
                    }
                    return row;
                })
                .collect(Collectors.toList());

        // ---- Count query (no join needed — projected columns don't affect row count) ----
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Vehicle> countRoot = countQuery.from(Vehicle.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(buildPredicate(cb, countRoot, companyId, ownerLedgerId));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<Vehicle> root, Long companyId, Long ownerLedgerId) {
        List<Predicate> predicates = new ArrayList<>();
        if (companyId != null) {
            predicates.add(cb.equal(root.get("companyId"), companyId));
        }
        if (ownerLedgerId != null) {
            predicates.add(cb.equal(root.get("ownerLedgerId"), ownerLedgerId));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Selection<?> mapFieldToSelection(Root<Vehicle> root, Join<Vehicle, Ledger> ledgerJoin, String field) {
        return switch (field) {
            case "ownerLedgerName" -> ledgerJoin.get("ledgerName").alias("ownerLedgerName");
            default -> root.get(field).alias(field); // vehicleId, companyId, vehicleNo, vehicleType, ownerLedgerId, createdAt, updatedAt
        };
    }
}