package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.AppInfoRequest;
import com.dominik.control.kidshieldbackend.model.AppInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppInfoMapper {

    AppInfo toEntity(AppInfoRequest appInfo);
}
