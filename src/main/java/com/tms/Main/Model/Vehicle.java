package com.tms.Main.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "vehicles", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @NotNull(message = "companyId is required")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotBlank(message = "vehicleNo cannot be blank")
    @Column(name = "vehicle_no", nullable = false, length = 50)
    private String vehicleNo;

    @Column(name = "vehicle_type", length = 100)
    @Size(max = 100, message = "vehicleType cannot exceed 100 characters")
    private String vehicleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_ledger_id",nullable = true)
    private Ledger ownerLedger;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
