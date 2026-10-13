package com.tms.Main.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "material_details",
        indexes = {
                @Index(name = "idx_material_details_company_id", columnList = "company_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "package", nullable = false)
    private String packageName;

    @Column(name = "description")
    private String description;

    @Column(name = "net_weight", precision = 19, scale = 4)
    private BigDecimal netWeight;

    @Column(name = "gross_weight", precision = 19, scale = 4)
    private BigDecimal grossWeight;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "dispatch_id")
    private Long dispatchDetails;

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
