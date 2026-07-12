package com.tms.Main.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.tms.Main.Dto.GroupRequestDTO;
import com.tms.Main.Dto.GroupResponseDTO;
import com.tms.Main.Model.Group;


@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(source = "companyProfile.companyId", target = "companyId")
    @Mapping(source = "parentGroup.groupId", target = "parentGroupId")
    @Mapping(source = "globalGroup.globalGroupId", target = "globalGroupId")
    GroupResponseDTO toDTO(Group group);

    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "companyProfile", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "globalGroup", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Group toEntity(GroupRequestDTO request);

    List<GroupResponseDTO> toDTOList(List<Group> groups);

    default Page<GroupResponseDTO> toDTOPage(Page<Group> groups) {
        return groups.map(this::toDTO);
    }

    // ✅ Project only selected fields from a DTO into a Map
    default Map<String, Object> toProjectedMap(GroupResponseDTO dto, Set<String> fields) {
        Map<String, Object> all = toFullMap(dto);
        if (fields == null || fields.isEmpty()) return all;

        Map<String, Object> projected = new LinkedHashMap<>();
        fields.forEach(f -> {
            if (all.containsKey(f)) projected.put(f, all.get(f));
        });
        return projected;
    }

    // ✅ Convert full DTO → Map (all columns) — no ObjectMapper needed
    default Map<String, Object> toFullMap(GroupResponseDTO dto) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("groupId",       dto.getGroupId());
        map.put("groupName",     dto.getGroupName());
        map.put("groupType",     dto.getGroupType());
        map.put("companyId",     dto.getCompanyId());
        map.put("parentGroupId", dto.getParentGroupId());
        map.put("globalGroupId", dto.getGlobalGroupId());
        map.put("createdAt",     dto.getCreatedAt());
        map.put("updatedAt",     dto.getUpdatedAt());
        return map;
    }

    // ✅ Page<Group> → Page<Map> with optional field projection
    default Page<Map<String, Object>> toDTOPageProjected(Page<Group> groups, Set<String> fields) {
        return groups.map(group -> {
            GroupResponseDTO dto = toDTO(group);
            return toProjectedMap(dto, fields);
        });
    }
}