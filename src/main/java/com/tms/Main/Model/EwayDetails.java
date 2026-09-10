package com.tms.Main.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "e_way_details",
        indexes = {
                @Index(name = "idx_eway_company_id", columnList = "company_id"),
                @Index(name = "idx_eway_trip_id", columnList = "trip_id"),
                @Index(name = "idx_eway_bill_date", columnList = "bill_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EwayDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_way_bill_id")
    private Long eWayBillId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @Column(name = "bill_no", nullable = false)
    private Long billNo;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
