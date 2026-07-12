package com.tms.Main.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.tms.Main.Dto.LedgerRequestDTO;
import com.tms.Main.Dto.LedgerResponseDTO;
import com.tms.Main.enumData.GroupType;

//service/LedgerService.java
public interface LedgerService {
	LedgerResponseDTO addLedger(LedgerRequestDTO request);

	Page<Map<String, Object>> getAllLedgers(Long companyId, List<String> select, List<GroupType> groupTypes, int page,
			int size);

	LedgerResponseDTO updateLedger(Long ledgerId, LedgerRequestDTO request);

	void deleteLedger(Long ledgerId);

	Map<String, Object> getLedgerById(Long ledgerId, List<String> select);
}
