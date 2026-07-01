package com.study.Main.Mapper;

import java.util.List;

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

 default Page<LedgerResponseDTO> toDTOPage(Page<Ledger> ledgers) {
     return ledgers.map(this::toDTO);
 }

// default Page<LedgerSummaryDTO> toSummaryDTOPage(Page<Ledger> ledgers) {
//     return ledgers.map(this::toSummaryDTO);
// }
}
