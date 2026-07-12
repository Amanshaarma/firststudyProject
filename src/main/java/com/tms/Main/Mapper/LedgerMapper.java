package com.study.Main.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.study.Main.Dto.GroupResponseDTO;
import com.study.Main.Model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.study.Main.Dto.LedgerRequestDTO;
import com.study.Main.Dto.LedgerResponseDTO;
import com.study.Main.Model.Ledger;

@Mapper(componentModel = "spring")
public interface LedgerMapper {

 @Mapping(source = "companyProfile.companyId", target = "companyId")
 @Mapping(source = "group.groupId", target = "groupId")
 LedgerResponseDTO toDTO(Ledger ledger);

 @Mapping(target = "ledgerId", ignore = true)
 @Mapping(target = "companyProfile", ignore = true)
 @Mapping(target = "group", ignore = true)
 @Mapping(target = "createdAt", ignore = true)
 @Mapping(target = "updatedAt", ignore = true)
 Ledger toEntity(LedgerRequestDTO request);

 List<LedgerResponseDTO> toDTOList(List<Ledger> ledgers);



// @Mapping(source = "companyProfile.companyId", target = "companyId")
// @Mapping(source = "group.groupId", target = "groupId")
// LedgerSummaryDTO toSummaryDTO(Ledger ledger);

// List<LedgerSummaryDTO> toSummaryDTOList(List<Ledger> ledgers);
default Page<Map<String, Object>> toDTOPageProjected(Page<Ledger> ledgers, Set<String> fields) {
    return ledgers.map(ledger -> {
        LedgerResponseDTO dto = toDTO(ledger);
        return toProjectedMap(dto, fields);
    });
}
    default Map<String, Object> toProjectedMap(LedgerResponseDTO dto, Set<String> fields) {
        Map<String, Object> all = toFullMap(dto);
        if (fields == null || fields.isEmpty()) return all;

        Map<String, Object> projected = new LinkedHashMap<>();
        fields.forEach(f -> {
            if (all.containsKey(f)) projected.put(f, all.get(f));
        });
        return projected;
    }
 default Page<LedgerResponseDTO> toDTOPage(Page<Ledger> ledgers) {
     return ledgers.map(this::toDTO);
 }

// default Page<LedgerSummaryDTO> toSummaryDTOPage(Page<Ledger> ledgers) {
//     return ledgers.map(this::toSummaryDTO);
// }


    default Map<String, Object> toFullMap(LedgerResponseDTO dto) {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("ledgerId", dto.getLedgerId());
        map.put("ledgerName", dto.getLedgerName());
        map.put("companyId", dto.getCompanyId());
        map.put("groupId", dto.getGroupId());
        map.put("openingBalance", dto.getOpeningBalance());
        map.put("isBillByBill", dto.getIsBillByBill());
        map.put("mailingName", dto.getMailingName());
        map.put("address", dto.getAddress());
        map.put("mobileNo", dto.getMobileNo());
        map.put("emailId", dto.getEmailId());
        map.put("gstNo", dto.getGstNo());
        map.put("panNo", dto.getPanNo());
        map.put("bankAcNo", dto.getBankAcNo());
        map.put("ifscCode", dto.getIfscCode());
        map.put("bankName", dto.getBankName());
        map.put("bankAcHolderName", dto.getBankAcHolderName());
        map.put("branchName", dto.getBranchName());
        map.put("createdAt", dto.getCreatedAt());
        map.put("updatedAt", dto.getUpdatedAt());
        map.put("addressLine1", dto.getAddressLine1());
        map.put("addressLine2", dto.getAddressLine2());
        map.put("state", dto.getState());
        map.put("country", dto.getCountry());
        map.put("pinCode", dto.getPinCode());

        return map;
    }
}
