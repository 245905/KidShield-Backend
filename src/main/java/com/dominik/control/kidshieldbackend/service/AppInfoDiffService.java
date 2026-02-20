package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.AppInfoDiffResponse;
import com.dominik.control.kidshieldbackend.model.AppInfo;
import com.dominik.control.kidshieldbackend.model.User;

import java.util.List;

public interface AppInfoDiffService {

    void calculateDiff(List<AppInfo> newEntities, List<AppInfo> oldEntities, User user, Long referenceNumber);
    List<AppInfoDiffResponse> retrieveData(Long userId);

}
