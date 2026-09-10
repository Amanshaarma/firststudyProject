package com.tms.Main.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "dispatch_details",
        indexes = {
                @Index(name = "idx_dispatch_company_id", columnList = "company_id"),
                @Index(name = "idx_dispatch_trip_id", columnList = "trip_id"),
                @Index(name = "idx_dispatch_material_id", columnList = "material_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispatchDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispatch_id")
    private Long dispatchId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "lr_date", nullable = false)
    private LocalDate lrDate;

    @Column(name = "lr_no", nullable = false)
    private Long lrNo;

    @Column(name = "net_wt", precision = 19, scale = 4)
    private BigDecimal netWeight;

    @Column(name = "gross_wt", precision = 19, scale = 4)
    private BigDecimal grossWeight;

    @Column(name = "dispatch_from", length = 255)
    private String dispatchFrom;

    @Column(name = "dispatch_to", length = 255)
    private String dispatchTo;

    @Column(name = "consignee_name", length = 255)
    private String consigneeName;

    @Column(name = "consignee_address", length = 500)
    private String consigneeAddress;

    @Column(name = "consignee_gstn", length = 50)
    private String consigneeGstn;

    @Column(name = "consignor_name", length = 255)
    private String consignorName;

    @Column(name = "consignor_address", length = 500)
    private String consignorAddress;

    @Column(name = "consignor_gstn", length = 50)
    private String consignorGstn;

    @Column(name = "material_id")
    private Long materialId;

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