package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.UserDto;
import com.dominik.control.kidshieldbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

}
