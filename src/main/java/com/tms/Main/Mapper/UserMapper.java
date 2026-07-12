package com.tms.Main.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tms.Main.Dto.UserRequestDTO;
import com.tms.Main.Dto.UserResponseDTO;
import com.tms.Main.Model.User;

//mapper/UserMapper.java
@Mapper(componentModel = "spring")
public interface UserMapper {

 // Entity → ResponseDTO
 UserResponseDTO toDTO(User user);

 // RequestDTO → Entity
 @Mapping(target = "userId", ignore = true)
 @Mapping(target = "passwordHash", ignore = true)  // set manually in service
 @Mapping(target = "createdAt", ignore = true)
 @Mapping(target = "updatedAt", ignore = true)
 User toEntity(UserRequestDTO request);

 List<UserResponseDTO> toDTOList(List<User> users);
}