package com.dominik.control.kidshieldbackend.mapper;

import com.dominik.control.kidshieldbackend.dto.PointRequest;
import com.dominik.control.kidshieldbackend.dto.PointResponse;
import com.dominik.control.kidshieldbackend.model.Point;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PointMapper {

    Point toEntity(PointRequest point);
    PointResponse toDto(Point point);
}
