package com.tms.Main.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tms.Main.Dto.CompanyRequestDTO;
import com.tms.Main.Dto.CompanyResponseDTO;
import com.tms.Main.Expection.DuplicateResourceException;
import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Mapper.CompanyMapper;
import com.tms.Main.Model.CompanyProfiles;
import com.tms.Main.Model.User;
import com.tms.Main.Repository.CompanyRepository;
import com.tms.Main.Repository.UserRepository;
import com.tms.Main.Service.CompanyService;

import jakarta.transaction.Transactional;

//service/impl/CompanyServiceImpl.java
@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final UserRepository usersRepository;
	private final CompanyMapper companyMapper;

	// D - Constructor injection
	public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository usersRepository,
			CompanyMapper companyMapper) {
		this.companyRepository = companyRepository;
		this.usersRepository = usersRepository;
		this.companyMapper = companyMapper;
	}

	@Override
	@Transactional
	public CompanyResponseDTO addCompany(CompanyRequestDTO request) {

		// 1. Validate Users exists
		User users = usersRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Users not found with id: " + request.getUserId()));

		// 2. Duplicate checks
		if (companyRepository.findByEmailId(request.getEmailId()).isPresent()) {
			throw new DuplicateResourceException("Email already registered: " + request.getEmailId());
		}

		if (companyRepository.findByMobileNo(request.getMobileNo()).isPresent()) {
			throw new DuplicateResourceException("Mobile number already registered: " + request.getMobileNo());
		}

		if (companyRepository.findByPanNumber(request.getPanNumber()).isPresent()) {
			throw new DuplicateResourceException("PAN already registered: " + request.getPanNumber());
		}

		if (request.getGstNo() != null && companyRepository.findByGstNo(request.getGstNo()).isPresent()) {
			throw new DuplicateResourceException("GST number already registered: " + request.getGstNo());
		}

		// 3. Map RequestDTO → Entity
		CompanyProfiles company = companyMapper.toEntity(request);
		company.setUser(users); // set Users manually

		// 4. Save & return
		CompanyProfiles savedCompany = companyRepository.save(company);
		return companyMapper.toDTO(savedCompany);
	}

	@Override
	public CompanyResponseDTO getCompanyById(Long companyId) {
		CompanyProfiles company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
		return companyMapper.toDTO(company);
	}

	@Override
	public List<CompanyResponseDTO> getCompaniesByUsersId(Long usersId) {
		List<CompanyProfiles> companies = companyRepository.findByUserUserId(usersId);
		return companyMapper.toDTOList(companies);
	}

	@Override
	public List<CompanyResponseDTO> getCompaniesByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}