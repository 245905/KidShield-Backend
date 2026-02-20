package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.dto.HourlyStatsResponse;
import com.dominik.control.kidshieldbackend.model.HourlyStats;

import java.util.Date;
import java.util.List;

public interface HourlyStatsService {

    List<HourlyStats> storeData(Long userId, List<HourlyStatsRequest> hourlyStatsRequests);
    List<HourlyStatsResponse> getByDate(Long userId, Date date);
    List<HourlyStatsResponse> getByName(Long userId, String name);
    List<HourlyStatsResponse> getByDateAndName(Long userId, Date date, String name);
}
