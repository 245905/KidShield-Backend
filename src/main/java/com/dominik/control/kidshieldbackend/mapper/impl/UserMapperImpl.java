package com.dominik.control.kidshieldbackend.mapper.impl;

import com.dominik.control.kidshieldbackend.dto.UserDto;
import com.dominik.control.kidshieldbackend.mapper.Mapper;
import com.dominik.control.kidshieldbackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<User, UserDto> {

    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
