package com.tms.Main.Model;


import com.tms.Main.enumData.GstRegistrationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity

@Table(name = "billing_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class BillingDetails {

    @Id
    @Column(name = "ledger_id")
    private Long ledgerId;

    @Column(name = "party_name", nullable = false, length = 255)
    private String partyName;

    @Column(name = "mailing_name", length = 255)
    private String mailingName;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "address_ln1", length = 255)
    private String addressLn1;

    @Column(name = "address_ln2", length = 255)
    private String addressLn2;

    @Column(name = "gst_registration_type", length = 50)
    @Enumerated(EnumType.STRING)
    private GstRegistrationType gstRegistrationType;

    @Column(name = "gst_no", length = 50)
    private String gstNo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ledger_id")
    private Ledger ledger;

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
