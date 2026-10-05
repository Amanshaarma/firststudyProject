package com.tms.Main.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tms.Main.enumData.GstRegType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
