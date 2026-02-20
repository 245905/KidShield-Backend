package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.dto.UsageStatsResponse;
import com.dominik.control.kidshieldbackend.model.UsageStats;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsageStatsMapper {

    UsageStats toEntity(UsageStatsRequest usageStats);
    UsageStatsResponse toDto(UsageStats usageStats);
}
