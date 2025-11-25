package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.model.UsageStats;

import java.util.List;

public interface UsageStatsService {

    List<UsageStats> storeData(Long userId, List<UsageStatsRequest> usageStatsRequests);
}
