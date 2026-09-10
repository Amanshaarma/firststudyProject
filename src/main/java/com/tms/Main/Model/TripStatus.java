package com.tms.Main.Model;

import com.tms.Main.enumData.StatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "trip_status",
        indexes = {
                @Index(name = "idx_trip_status_company_id", columnList = "company_id"),
                @Index(name = "idx_trip_status_trip_id", columnList = "trip_id"),
                @Index(name = "idx_trip_status_date", columnList = "date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_status_id")
    private Long tripStatusId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type", nullable = false, length = 50)
    private StatusType statusType;

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