package com.tms.Main.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.tms.Main.enumData.GroupType;

@Entity
@Table(name = "groups", indexes = { @Index(name = "idx_group_name", columnList = "group_name"),
		@Index(name = "idx_group_type", columnList = "group_type"),
		@Index(name = "idx_company_id", columnList = "company_id") })


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "group_id_seq", allocationSize = 20)
    @Column(name = "group_id")
    private Long groupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "global_group_id")
	private GlobalGroup globalGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
    @NotNull(message = "Company ID is required")
	private CompanyProfiles companyProfile;

	// Self referencing — a group can have a parent group
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_group_id")
	private Group parentGroup;

	@Column(name = "group_name", nullable = false)
    @NotBlank(message = "Group name is required")
	private String groupName;

	@Enumerated(EnumType.STRING)
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name = "group_type", columnDefinition = "group_type_enum")
    @NotNull(message = "Group type is required")
	private GroupType groupType;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

    @Column(name = "is_default")
    private Boolean isDefault;


	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", globalGroup=" + globalGroup +
                ", companyProfile=" + companyProfile +
                ", parentGroup=" + parentGroup +
                ", groupName='" + groupName + '\'' +
                ", groupType=" + groupType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDefault=" + isDefault +
                '}';
    }
}