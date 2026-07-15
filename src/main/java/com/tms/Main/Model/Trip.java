package com.tms.Main.Model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyProfiles companyProfile;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sale_voucher_id")
//    private Voucher saleVoucher;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "purchase_voucher_id")
//    private Voucher purchaseVoucher;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Column(name = "lr_no", length = 100)
    private String lrNo;

    @Column(name = "from_place", length = 255)
    private String fromPlace;

    @Column(name = "to_place", length = 255)
    private String toPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_ledger_id")
    private Ledger partyLedger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_ledger_id")
    private Ledger supplierLedger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_ledger_id")
    private Ledger salesLedger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_ledger_id")
    private Ledger purchaseLedger;

    @Column(name = "party_weight", precision = 18, scale = 3)
    private BigDecimal partyWeight;

    @Column(name = "party_rate", precision = 18, scale = 2)
    private BigDecimal partyRate;

    @Column(name = "supplier_weight", precision = 18, scale = 3)
    private BigDecimal supplierWeight;

    @Column(name = "supplier_rate", precision = 18, scale = 2)
    private BigDecimal supplierRate;

    @Column(name = "party_freight", precision = 18, scale = 2)
    private BigDecimal partyFreight;

    @Column(name = "supplier_freight", precision = 18, scale = 2)
    private BigDecimal supplierFreight;

    @Column(name = "party_freight_type", length = 50)
    private String partyFreightType;

    @Column(name = "supplier_freight_type", length = 50)
    private String supplierFreightType;

    @Column(name = "material", length = 255)
    private String material;

    @Column(name = "pod_rcv_date")
    private LocalDate podRcvDate;

    @Column(name = "pod_submit_date")
    private LocalDate podSubmitDate;

    @Column(name = "ulpoint_date")
    private LocalDate ulpointDate;

    @Column(name = "trip_completed_date")
    private LocalDate tripCompletedDate;

    @Column(name = "narration", columnDefinition = "TEXT")
    private String narration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}