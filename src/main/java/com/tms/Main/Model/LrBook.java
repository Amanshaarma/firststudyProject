package com.tms.Main.Model;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "lr_book",
        indexes = {
                @Index(name = "idx_lr_book_company_id", columnList = "company_id"),
                @Index(name = "idx_lr_book_vehicle_id", columnList = "vehicle_id"),
                @Index(name = "idx_lr_book_trip_id", columnList = "trip_id"),
                @Index(name = "idx_lr_book_lr_date", columnList = "lr_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LrBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lr_id")
    private Long lrId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "lr_date", nullable = false)
    private LocalDate lrDate;

    @Column(name = "lr_no", nullable = false)
    private Long lrNo;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "from_city")
    private String fromCity;

    @Column(name = "to_city")
    private String toCity;

    @Column(name = "consignee_id")
    private Long consigneeId;

    @Column(name = "consignor_id")
    private Long consignorId;

    @Column(name = "consignee_name")
    private String consigneeName;

    @Column(name = "consignee_address")
    private String consigneeAddress;

    @Column(name = "consignee_address_ln1")
    private String consigneeAddressLn1;

    @Column(name = "consignee_address_ln2")
    private String consigneeAddressLn2;

    @Column(name = "consignee_gst_no")
    private String consigneeGstNo;

    @Column(name = "consignor_name")
    private String consignorName;

    @Column(name = "consignor_address")
    private String consignorAddress;

    @Column(name = "consignor_address_ln1")
    private String consignorAddressLn1;

    @Column(name = "consignor_address_ln2")
    private String consignorAddressLn2;

    @Column(name = "consignor_gst_no")
    private String consignorGstNo;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /*
     * One Trip can contain many LR records.
     * lr_book is the owning side because trip_id
     * is stored in the lr_book table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

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