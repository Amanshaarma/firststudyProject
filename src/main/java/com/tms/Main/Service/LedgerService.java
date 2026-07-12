package com.study.Main.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.study.Main.Dto.LedgerRequestDTO;
import com.study.Main.Dto.LedgerResponseDTO;
import com.study.Main.enumData.GroupType;

//service/LedgerService.java
public interface LedgerService {
	LedgerResponseDTO addLedger(LedgerRequestDTO request);

	Page<Map<String, Object>> getAllLedgers(Long companyId, List<String> select, List<GroupType> groupTypes, int page,
			int size);

	LedgerResponseDTO updateLedger(Long ledgerId, LedgerRequestDTO request);

	void deleteLedger(Long ledgerId);

	Map<String, Object> getLedgerById(Long ledgerId, List<String> select);
}
