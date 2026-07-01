package com.study.Main.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.Main.Dto.LedgerResponseDTO;
import com.study.Main.Expection.BadRequestException;

@Component
public class ColumnFilterUtil {


	// Convert DTO → Map using Jackson
	private Map<String, Object> convertToMap(LedgerResponseDTO dto) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.convertValue(dto, new TypeReference<Map<String, Object>>() {
		});
	}

	// Filter list of DTOs
	public List<Map<String, Object>> filterColumnsList(List<LedgerResponseDTO> dtos, List<String> select) {
		return dtos.stream().map(dto -> filterColumns(dto, select)).collect(Collectors.toList());
	}

	// Filter page of DTOs
	public Page<Map<String, Object>> filterColumnsPage(Page<LedgerResponseDTO> dtoPage, List<String> select) {
		return dtoPage.map(dto -> filterColumns(dto, select));
	}
	// In ColumnFilterUtil.java — add validation
	public Map<String, Object> filterColumns(
	        LedgerResponseDTO dto, List<String> select) {

	    if (select == null || select.isEmpty()) {
	        return convertToMap(dto);
	    }

	    // ✅ Validate column names first
	    select.forEach(column -> {
	        if (!LedgerColumns.VALID_COLUMNS.contains(column)) {
	            throw new BadRequestException("Invalid column name: " + column);
	        }
	    });

	    Map<String, Object> fullMap = convertToMap(dto);
	    Map<String, Object> filteredMap = new LinkedHashMap<>();
	    select.forEach(column -> filteredMap.put(column, fullMap.get(column)));

	    return filteredMap;
	}
}