package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.AppInfoDiffResponse;
import com.dominik.control.kidshieldbackend.model.AppInfoDiff;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppInfoDiffMapper {

    AppInfoDiffResponse toDto(AppInfoDiff appInfoDiff);
}
