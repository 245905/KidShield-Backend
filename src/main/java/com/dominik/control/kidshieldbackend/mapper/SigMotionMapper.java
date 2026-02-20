package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.SigMotionRequest;
import com.dominik.control.kidshieldbackend.dto.SigMotionResponse;
import com.dominik.control.kidshieldbackend.model.SigMotion;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SigMotionMapper {

    SigMotion toEntity(SigMotionRequest sigMotion);
    SigMotionResponse toDto(SigMotion sigMotion);
}
