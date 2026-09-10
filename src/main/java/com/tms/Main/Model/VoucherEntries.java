package com.tms.Main.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "voucher_entries",
        indexes = {
                @Index(name = "idx_voucher_entries_voucher_id", columnList = "voucher_id"),
                @Index(name = "idx_voucher_entries_ledger_id", columnList = "ledger_id"),
                @Index(name = "idx_voucher_entries_company_id", columnList = "company_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long entryId;

    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;

    @Column(name = "ledger_id", nullable = false)
    private Long ledgerId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
