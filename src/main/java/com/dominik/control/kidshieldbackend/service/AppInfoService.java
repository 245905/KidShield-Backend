package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.AppInfoRequest;
import com.dominik.control.kidshieldbackend.model.AppInfo;

import java.util.List;

public interface AppInfoService {

    List<AppInfo> storeData(Long userId, List<AppInfoRequest> appInfoRequests);
}
