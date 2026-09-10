package com.tms.Main.Model;

import com.tms.Main.Dto.VoucherCounterId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "voucher_counter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherCounter {

    @EmbeddedId
    private VoucherCounterId id;

    @Column(name = "last_voucher_no", nullable = false)
    private Long lastVoucherNo;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}