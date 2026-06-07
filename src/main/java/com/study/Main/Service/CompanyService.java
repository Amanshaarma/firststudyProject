package com.study.Main.Service;

import java.util.List;

import com.study.Main.Dto.CompanyRequestDTO;
import com.study.Main.Dto.CompanyResponseDTO;

public interface CompanyService {
	CompanyResponseDTO addCompany(CompanyRequestDTO request);

	CompanyResponseDTO getCompanyById(Long companyId);

	List<CompanyResponseDTO> getCompaniesByUserId(Long userId);

	List<CompanyResponseDTO> getCompaniesByUsersId(Long usersId);
}