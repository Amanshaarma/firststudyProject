package com.tms.Main.Service;

import java.util.List;

import com.tms.Main.Dto.CompanyRequestDTO;
import com.tms.Main.Dto.CompanyResponseDTO;

public interface CompanyService {
	CompanyResponseDTO addCompany(CompanyRequestDTO request);

	CompanyResponseDTO getCompanyById(Long companyId);


	List<CompanyResponseDTO> getCompaniesByUserId(Long usersId);
}