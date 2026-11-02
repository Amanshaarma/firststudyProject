package com.tms.Main.Model;

import com.tms.Main.enumData.MethodOfAdj;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity(name = "bill_references")
@Builder
@Data
public class BillReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_ref_id")
    private Long billRefId;

    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;

    @Column(name = "ledger_id", nullable = false)
    @NotNull( message = "Ledger id is not present")
    private Long ledgerId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "ref_name")
    private String refName;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "mthd_of_adj", columnDefinition = "method_of_adjustment_t")
    private MethodOfAdj mthdOfAdj;

    @Column(name = "agst_bill_ref_id")
    private Long agstBillReference;

}
