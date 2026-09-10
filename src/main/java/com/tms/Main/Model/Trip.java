package com.tms.Main.Model;

import com.tms.Main.enumData.FreightType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "trips",
        indexes = {
                @Index(name = "idx_trip_company_id", columnList = "company_id"),
                @Index(name = "idx_trip_vehicle_id", columnList = "vehicle_id"),
                @Index(name = "idx_trip_date", columnList = "trip_date"),
                @Index(name = "idx_trip_lr_id", columnList = "lr_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "sale_voucher_id")
    private Long saleVoucherId;

    @Column(name = "purchase_voucher_id")
    private Long purchaseVoucherId;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "party_ledger_id", nullable = false)
    private Long partyLedgerId;

    @Column(name = "supplier_ledger_id", nullable = false)
    private Long supplierLedgerId;

    @Column(name = "sales_ledger_id", nullable = false)
    private Long salesLedgerId;

    @Column(name = "purchase_ledger_id", nullable = false)
    private Long purchaseLedgerId;

    @Column(name = "party_weight", nullable = false, precision = 19, scale = 4)
    private BigDecimal partyWeight;

    @Column(name = "party_rate", nullable = false, precision = 19, scale = 4)
    private BigDecimal partyRate;

    @Column(name = "supplier_weight", nullable = false, precision = 19, scale = 4)
    private BigDecimal supplierWeight;

    @Column(name = "supplier_rate", nullable = false, precision = 19, scale = 4)
    private BigDecimal supplierRate;

    @Column(name = "party_freight", nullable = false, precision = 19, scale = 4)
    private BigDecimal partyFreight;

    @Column(name = "supplier_freight", nullable = false, precision = 19, scale = 4)
    private BigDecimal supplierFreight;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
            name = "party_freight_type",
            nullable = false,
            columnDefinition = "freight_type_t"
    )
    private FreightType partyFreightType;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
            name = "supplier_freight_type",
            nullable = false,
            columnDefinition = "freight_type_t"
    )
    private FreightType supplierFreightType;

    @Column(name = "narration", nullable = false, length = 1000)
    private String narration;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "dispatch_details_id")
    private Long dispatchDetailsId;

    @Column(name = "material_inv_id")
    private Long materialInvId;

    @Column(name = "receipt_details_id")
    private Long receiptDetailsId;

    @Column(name = "e_way_bill_id")
    private Long eWayBillId;

    @Column(name = "party_billing_details_id")
    private Long partyBillingDetailsId;

    @Column(name = "supplier_billing_details_id")
    private Long supplierBillingDetailsId;

    @Column(name = "pod_rcv_id")
    private Long podRcvId;

    @Column(name = "pod_submit_id")
    private Long podSubmitId;

    @Column(name = "ul_point_id")
    private Long ulPointId;

    @Column(name = "trip_completed_id")
    private Long tripCompletedId;

    @Column(name = "from_city_id", nullable = false)
    private Long fromCityId;

    @Column(name = "to_city_id", nullable = false)
    private Long toCityId;

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