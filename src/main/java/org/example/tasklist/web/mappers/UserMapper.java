package org.example.tasklist.web.mappers;

import org.example.tasklist.domain.user.User;
import org.example.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto (User user);
    User toEntity(UserDto dto);
}
