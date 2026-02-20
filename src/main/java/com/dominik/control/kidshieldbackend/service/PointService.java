package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.PointRequest;
import com.dominik.control.kidshieldbackend.dto.PointResponse;
import com.dominik.control.kidshieldbackend.model.Point;

import java.util.List;

public interface PointService {
    List<Point> storeData(Long userId, List<PointRequest> pointRequests);
    List<PointResponse> getBetween(Long userId, Long startTime, Long endTime);
    List<PointResponse> getRecent(Long userId, Long time);
}
