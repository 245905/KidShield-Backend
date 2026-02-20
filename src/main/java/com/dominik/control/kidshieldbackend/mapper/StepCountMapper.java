package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.StepCountRequest;
import com.dominik.control.kidshieldbackend.dto.StepCountResponse;
import com.dominik.control.kidshieldbackend.model.StepCount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StepCountMapper {

    StepCount toEntity(StepCountRequest stepCount);
    StepCountResponse toDto(StepCount stepCount);
}
