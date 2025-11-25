package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.model.HourlyStats;

import java.util.List;

public interface HourlyStatsService {

    List<HourlyStats> storeData(Long userId, List<HourlyStatsRequest> hourlyStatsRequests);
}
