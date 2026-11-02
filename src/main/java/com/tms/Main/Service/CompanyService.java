package com.tms.Main.Service;

import com.tms.Main.Dto.CompanyRequestDTO;
import com.tms.Main.Dto.CompanyResponseDTO;
import com.tms.Main.Model.CompanyProfiles;

import java.util.List;

public interface CompanyService {
	CompanyResponseDTO addCompany(CompanyRequestDTO request);

	CompanyResponseDTO getCompanyByIdByDto(Long companyId);


	List<CompanyResponseDTO> getCompaniesByUserId(Long usersId);

    CompanyProfiles getCompanyById(Long companyId);
}