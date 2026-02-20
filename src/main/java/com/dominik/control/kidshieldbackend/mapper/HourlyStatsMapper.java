package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.dto.HourlyStatsResponse;
import com.dominik.control.kidshieldbackend.model.HourlyStats;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HourlyStatsMapper {

    HourlyStats toEntity(HourlyStatsRequest hourlyStats);
    HourlyStatsResponse toDto(HourlyStats hourlyStats);
}
