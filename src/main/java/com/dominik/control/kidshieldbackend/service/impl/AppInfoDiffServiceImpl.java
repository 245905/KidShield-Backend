package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.AppInfoDiffResponse;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.AppInfoDiffMapper;
import com.dominik.control.kidshieldbackend.model.AppChangeType;
import com.dominik.control.kidshieldbackend.model.AppInfo;
import com.dominik.control.kidshieldbackend.model.AppInfoDiff;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.AppInfoDiffRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.AppInfoDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppInfoDiffServiceImpl implements AppInfoDiffService {

    private final AppInfoDiffRepository appInfoDiffRepository;
    private final UserRepository userRepository;

    private final AppInfoDiffMapper appInfoDiffMapper;

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
        })
                .filter(Objects::nonNull)
                .toList();

        if(!addedDiffs.isEmpty()) appInfoDiffRepository.saveAll(addedDiffs);
        if(!removedDiffs.isEmpty()) appInfoDiffRepository.saveAll(removedDiffs);
        if(!updatedDiffs.isEmpty()) appInfoDiffRepository.saveAll(updatedDiffs);
    }

    @Override
    @Transactional
    public List<AppInfoDiffResponse> retrieveData(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<AppInfoDiff> entities = appInfoDiffRepository.findAllByUserWithMaxReferenceNumber(monitored);

        return entities.stream().map(appInfoDiffMapper::toDto).toList();
    }
}
