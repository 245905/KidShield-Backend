package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.SigMotionRequest;
import com.dominik.control.kidshieldbackend.dto.SigMotionResponse;
import com.dominik.control.kidshieldbackend.model.SigMotion;

import java.util.List;

public interface SigMotionService {
    List<SigMotion> storeData(Long userId, List<SigMotionRequest> sigMotionRequests);
    List<SigMotionResponse> getBetween(Long userId, Long startTime, Long endTime);
    List<SigMotionResponse> getRecent(Long userId, Long time);
}
