package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.dto.UsageStatsResponse;
import com.dominik.control.kidshieldbackend.model.UsageStats;

import java.util.Date;
import java.util.List;

public interface UsageStatsService {

    List<UsageStats> storeData(Long userId, List<UsageStatsRequest> usageStatsRequests);
    List<UsageStatsResponse> getByDate(Long userId, Date date);
    List<UsageStatsResponse> getByName(Long userId, String name);
    List<UsageStatsResponse> getByDateAndName(Long userId, Date date, String name);

}
