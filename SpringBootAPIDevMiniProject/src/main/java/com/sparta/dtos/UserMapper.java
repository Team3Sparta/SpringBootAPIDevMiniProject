package com.sparta.dtos;


import com.sparta.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(AppUser user);
    AppUser toEntity(UserDTO userDTO);
}
