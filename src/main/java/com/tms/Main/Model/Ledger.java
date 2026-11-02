package com.tms.Main.Model;

import com.tms.Main.enumData.GstRegType;
import com.tms.Main.enumData.LedgerType;
import com.tms.Main.enumData.SupplyType;
import com.tms.Main.enumData.TaxType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledgers", indexes = { @Index(name = "idx_ledger_company_id", columnList = "company_id"),
		@Index(name = "idx_ledger_group_id", columnList = "group_id"),
		@Index(name = "idx_ledger_name", columnList = "ledger_name") })
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ledger_id")
	private Long ledgerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyProfiles companyProfile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@Column(name = "ledger_name", nullable = false)
	private String ledgerName;

	@Column(name = "opening_balance", nullable = false)
	private BigDecimal openingBalance = BigDecimal.ZERO;

	@Column(name = "is_bill_by_bill", nullable = false)
	private Boolean isBillByBill = false;

	@Column(name = "mailing_name")
	private String mailingName;

	@Column(name = "address")
	private String address;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "gst_no")
	private String gstNo;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gst_reg_type", columnDefinition = "gst_reg_type_t")
    private GstRegType gstRegType;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "bank_ac_no")
	private String bankAcNo;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_ac_holder_name")
	private String bankAcHolderName;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

    @Column(name = "address_ln1")
    private String addressLine1;

    @Column(name = "address_ln2")
    private String addressLine2;

    private String state;
    private String country;

    @Column(name = "pin_code")
    private String pinCode;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "type_of_ledger", columnDefinition = "ledger_type_enum")
    private LedgerType typeOfLedger;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "tax_type", columnDefinition = "tax_type_enum")
    private TaxType taxType;

    @Column(name = "is_gst_applicable", nullable = false)
    private Boolean isGstApplicable = false;

    @Column(name = "hsn_sac", length = 8)
    private String hsnSac;

    @Column(name = "gst_rate", precision = 5, scale = 2)
    private BigDecimal gstRate;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "type_of_supply", columnDefinition = "supply_type_enum")
    private SupplyType typeOfSupply;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
