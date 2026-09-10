package com.tms.Main.Model;

import com.tms.Main.enumData.SourceType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "vouchers",
        indexes = {
                @Index(name = "idx_voucher_company_id", columnList = "company_id"),
                @Index(name = "idx_voucher_type_id", columnList = "voucher_type_id"),
                @Index(name = "idx_voucher_date", columnList = "voucher_date")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_voucher_company_year_no",
                        columnNames = {"company_id", "fiscal_year", "voucher_no"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vouchers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "voucher_type_id", nullable = false)
    private Long voucherTypeId;

    @Column(name = "voucher_no", nullable = false)
    private Long voucherNo;

    @Column(name = "voucher_date", nullable = false)
    private LocalDate voucherDate;

    @Column(name = "fiscal_year", nullable = false, length = 20)
    private String fiscalYear;

    @Column(name = "narration", length = 1000)
    private String narration;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 50)
    private SourceType sourceType;

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
