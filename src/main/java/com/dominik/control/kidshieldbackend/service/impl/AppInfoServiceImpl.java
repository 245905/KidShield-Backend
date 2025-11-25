package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.AppInfoRequest;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.AppInfoMapper;
import com.dominik.control.kidshieldbackend.model.AppInfo;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.AppInfoRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.AppInfoDiffService;
import com.dominik.control.kidshieldbackend.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppInfoServiceImpl implements AppInfoService {

    private final AppInfoRepository appInfoRepository;
    private final UserRepository userRepository;
    private final AppInfoDiffService appInfoDiffService;

    private final AppInfoMapper appInfoMapper;

    @Override
    @Transactional
    public List<AppInfo> storeData(Long userId, List<AppInfoRequest> appInfoRequests) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        Long referenceNumber = appInfoRepository.findMaxReferenceNumber(user);
        if(referenceNumber == null) referenceNumber = 1L;
        else referenceNumber++;

        Long finalReferenceNumber = referenceNumber;
        List<AppInfo> entities = appInfoRequests.stream()
                .map(appInfoMapper::toEntity)
                .peek(entity -> entity.setReferenceNumber(finalReferenceNumber))
                .peek(entity -> entity.setUser(user))
                .toList();

        List<AppInfo> oldEntities = appInfoRepository.findAllByUserWithMaxReferenceNumber(user);
        entities = appInfoRepository.saveAll(entities);

        // calculating diff
        appInfoDiffService.calculateDiff(entities, oldEntities, user, finalReferenceNumber);

        return entities;
    }
}
