package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.StepCountRequest;
import com.dominik.control.kidshieldbackend.dto.StepCountResponse;
import com.dominik.control.kidshieldbackend.model.StepCount;

import java.util.List;

public interface StepCountService {
    List<StepCount> storeData(Long userId, List<StepCountRequest> stepCountRequests);
    List<StepCountResponse> getBetween(Long userId, Long startTime, Long endTime);
    List<StepCountResponse> getRecent(Long userId, Long time);
}
