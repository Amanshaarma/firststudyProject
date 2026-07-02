package com.study.Main.Service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.study.Main.enumData.GroupType;
import com.study.Main.Dto.LedgerRequestDTO;
import com.study.Main.Dto.LedgerResponseDTO;
import com.study.Main.Expection.BadRequestException;
import com.study.Main.Expection.CompanyNofFound;
import com.study.Main.Expection.DuplicateResourceException;
import com.study.Main.Expection.ResourceNotFoundException;
import com.study.Main.Mapper.LedgerMapper;
import com.study.Main.Model.CompanyProfiles;
import com.study.Main.Model.Group;
import com.study.Main.Model.Ledger;
import com.study.Main.Repository.CompanyRepository;
import com.study.Main.Repository.GroupRepository;
import com.study.Main.Repository.LedgerRepository;
import com.study.Main.Service.LedgerService;
import com.study.Main.util.ColumnFilterUtil;

import jakarta.transaction.Transactional;

//service/impl/LedgerServiceImpl.java
@Service
public class LedgerServiceImpl implements LedgerService {

	private final LedgerRepository ledgerRepository;
	private final CompanyRepository companyProfileRepository;
	private final GroupRepository groupRepository;
	private final LedgerMapper ledgerMapper;
	private final ColumnFilterUtil columnFilterUtil; // ✅ inject filter util

	public LedgerServiceImpl(LedgerRepository ledgerRepository, CompanyRepository companyProfileRepository,
			GroupRepository groupRepository, LedgerMapper ledgerMapper, ColumnFilterUtil columnFilterUtil) {
		this.ledgerRepository = ledgerRepository;
		this.companyProfileRepository = companyProfileRepository;
		this.groupRepository = groupRepository;
		this.ledgerMapper = ledgerMapper;
		this.columnFilterUtil = columnFilterUtil;
	}

	// ✅ GET by ID
	@Override
	public Map<String, Object> getLedgerById(Long ledgerId, List<String> select) {

		Ledger ledger = ledgerRepository.findById(ledgerId)
				.orElseThrow(() -> new ResourceNotFoundException("Ledger not found: " + ledgerId));

		LedgerResponseDTO dto = ledgerMapper.toDTO(ledger);

		// Filter columns based on select param
		return columnFilterUtil.filterColumns(dto, select);
	}

	// ✅ GET All
	@Override
	public Page<Map<String, Object>> getAllLedgers(Long companyId, List<String> select, List<GroupType> groupTypes,
			int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("ledgerName").ascending());

		Page<Ledger> ledgers;

		// Case 1 — company + groupType
		if (companyId != null && groupTypes != null && !groupTypes.isEmpty()) {
			ledgers = ledgerRepository.findByCompanyIdAndGroupTypes(companyId, groupTypes, pageable);
		}
		// Case 2 — groupType only
		else if (groupTypes != null && !groupTypes.isEmpty()) {
			ledgers = ledgerRepository.findByGroupTypes(groupTypes, pageable);
		}
		// Case 3 — company only
		else if (companyId != null) {
			ledgers = ledgerRepository.findByCompanyProfileCompanyId(companyId, pageable);
		}
		// Case 4 — No params
		else {
			ledgers = ledgerRepository.findAll(pageable);
		}

		// Map to DTO then filter columns
		Page<LedgerResponseDTO> dtoPage = ledgers.map(ledgerMapper::toDTO);
		return columnFilterUtil.filterColumnsPage(dtoPage, select);
	}

//✅ POST
	@Override
	@Transactional
	public LedgerResponseDTO addLedger(LedgerRequestDTO request) {

		// 1. Validate company
		CompanyProfiles company = companyProfileRepository.findById(request.getCompanyId())
				.orElseThrow(() -> new ResourceNotFoundException("Company not found: " + request.getCompanyId()));

		// 2. Validate group exists in same company
		Group group = groupRepository.findById(request.getGroupId())
				.orElseThrow(() -> new ResourceNotFoundException("Group not found: " + request.getGroupId()));

		if (!group.getCompanyProfile().getCompanyId().equals(request.getCompanyId())) {
			throw new BadRequestException("Group does not belong to company: " + request.getCompanyId());
		}

		// 3. Duplicate ledger name in same company
		if (ledgerRepository.existsByLedgerNameAndCompanyProfileCompanyId(request.getLedgerName(),
				request.getCompanyId())) {
			throw new DuplicateResourceException("Ledger already exists: " + request.getLedgerName());
		}

		// 4. Map & save
		Ledger ledger = ledgerMapper.toEntity(request);
		ledger.setCompanyProfile(company);
		ledger.setGroup(group);

		return ledgerMapper.toDTO(ledgerRepository.save(ledger));
	}

	// ✅ UPDATE
	@Override
	@Transactional
	public LedgerResponseDTO updateLedger(Long ledgerId, LedgerRequestDTO request) {

		// 1. Check ledger exists
		Ledger ledger = ledgerRepository.findById(ledgerId)
				.orElseThrow(() -> new ResourceNotFoundException("Ledger not found: " + ledgerId));

		// 2. Validate group exists in same company
		Group group = groupRepository.findById(request.getGroupId())
				.orElseThrow(() -> new ResourceNotFoundException("Group not found: " + request.getGroupId()));

		if (!group.getCompanyProfile().getCompanyId().equals(request.getCompanyId())) {
			throw new BadRequestException("Group does not belong to company: " + request.getCompanyId());
		}

		// 3. Duplicate check — exclude current ledger
		if (!ledger.getLedgerName().equals(request.getLedgerName()) && ledgerRepository
				.existsByLedgerNameAndCompanyProfileCompanyId(request.getLedgerName(), request.getCompanyId())) {
			throw new DuplicateResourceException("Ledger already exists: " + request.getLedgerName());
		}

		// 4. Update fields
		ledger.setLedgerName(request.getLedgerName());
		ledger.setGroup(group);
		ledger.setOpeningBalance(request.getOpeningBalance());
		ledger.setIsBillByBill(request.getIsBillByBill());
		ledger.setMailingName(request.getMailingName());
		ledger.setAddress(request.getAddress());
		ledger.setMobileNo(request.getMobileNo());
		ledger.setEmailId(request.getEmailId());
		ledger.setGstNo(request.getGstNo());
		ledger.setPanNo(request.getPanNo());
		ledger.setBankAcNo(request.getBankAcNo());
		ledger.setIfscCode(request.getIfscCode());
		ledger.setBankName(request.getBankName());
		ledger.setBankAcHolderName(request.getBankAcHolderName());
		ledger.setBranchName(request.getBranchName());

		return ledgerMapper.toDTO(ledgerRepository.save(ledger));
	}

	// ✅ DELETE — only if no children
	@Override
	@Transactional
	public void deleteLedger(Long ledgerId) {

		Ledger ledger = ledgerRepository.findById(ledgerId)
				.orElseThrow(() -> new ResourceNotFoundException("Ledger not found: " + ledgerId));

		ledgerRepository.deleteById(ledgerId);
	}

}