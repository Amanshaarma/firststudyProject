package com.tms.Main.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherCounterId implements Serializable {

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "financial_year")
    private String financialYear;
}
