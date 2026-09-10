package com.tms.Main.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "receipt_details",
        indexes = {
                @Index(name = "idx_receipt_company_id", columnList = "company_id"),
                @Index(name = "idx_receipt_trip_id", columnList = "trip_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "supplier_inv_date", nullable = false)
    private LocalDate supplierInvDate;

    @Column(name = "supplier_inv_no")
    private String supplierInvNo;

    @Column(name = "receipt_doc_no")
    private String receiptDocNo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}