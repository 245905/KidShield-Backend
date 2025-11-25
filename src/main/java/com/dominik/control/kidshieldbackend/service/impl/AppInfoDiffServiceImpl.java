package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.model.AppChangeType;
import com.dominik.control.kidshieldbackend.model.AppInfo;
import com.dominik.control.kidshieldbackend.model.AppInfoDiff;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.AppInfoDiffRepository;
import com.dominik.control.kidshieldbackend.service.AppInfoDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppInfoDiffServiceImpl implements AppInfoDiffService {

    private final AppInfoDiffRepository appInfoDiffRepository;

    @Override
    @Transactional
    public void calculateDiff(List<AppInfo> newEntities, List<AppInfo> oldEntities, User user, Long referenceNumber) {
        Map<String, AppInfo> newApps = newEntities.stream().
                collect(Collectors.toMap(AppInfo::getPackageName, Function.identity()));

        Map<String, AppInfo> oldApps = oldEntities.stream().
                collect(Collectors.toMap(AppInfo::getPackageName, Function.identity()));

        Set<String> newKeys = newApps.keySet();
        Set<String> oldKeys = oldApps.keySet();

        Set<String> added = new HashSet<>(newKeys);
        added.removeAll(oldKeys);

        Set<String> removed = new HashSet<>(oldKeys);
        removed.removeAll(newKeys);

        Set<String> possiblyUpdated = new HashSet<>(newKeys);
        possiblyUpdated.retainAll(oldKeys);

        List<AppInfoDiff> addedDiffs = added.stream().map(a -> {
            AppInfo app = newApps.get(a);
            return AppInfoDiff.builder()
                    .referenceNumber(referenceNumber)
                    .changeType(AppChangeType.ADDED)
                    .appName(app.getAppName())
                    .packageName(app.getPackageName())
                    .oldVersionName(app.getVersionName())
                    .oldVersionCode(app.getVersionCode())
                    .lastUpdateTime(app.getLastUpdateTime())
                    .user(user)
                    .build();
        }).toList();

        List<AppInfoDiff> removedDiffs = removed.stream().map(a -> {
            AppInfo app = oldApps.get(a);
            return AppInfoDiff.builder()
                    .referenceNumber(referenceNumber)
                    .changeType(AppChangeType.REMOVED)
                    .appName(app.getAppName())
                    .packageName(app.getPackageName())
                    .oldVersionName(app.getVersionName())
                    .oldVersionCode(app.getVersionCode())
                    .lastUpdateTime(app.getLastUpdateTime())
                    .user(user)
                    .build();
        }).toList();

        List<AppInfoDiff> updatedDiffs = possiblyUpdated.stream().map(a -> {
            AppInfo oldApp = oldApps.get(a);
            AppInfo newApp = newApps.get(a);
            boolean versionChanged = !oldApp.getVersionCode().equals(newApp.getVersionCode())
                    || !oldApp.getVersionName().equals(newApp.getVersionName())
                    || !oldApp.getLastUpdateTime().equals(newApp.getLastUpdateTime());
            if(!versionChanged) return null;
            return AppInfoDiff.builder()
                    .referenceNumber(referenceNumber)
                    .changeType(AppChangeType.UPDATED)
                    .appName(oldApp.getAppName())
                    .packageName(oldApp.getPackageName())
                    .oldVersionName(oldApp.getVersionName())
                    .oldVersionCode(oldApp.getVersionCode())
                    .lastUpdateTime(oldApp.getLastUpdateTime())
                    .user(user)
                    .build();
        }).toList();

        appInfoDiffRepository.saveAll(addedDiffs);
        appInfoDiffRepository.saveAll(removedDiffs);
        appInfoDiffRepository.saveAll(updatedDiffs);
    }
}
