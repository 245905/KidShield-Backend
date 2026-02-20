package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.StepCountRequest;
import com.dominik.control.kidshieldbackend.dto.StepCountResponse;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.StepCountMapper;
import com.dominik.control.kidshieldbackend.model.StepCount;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.StepCountRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.StepCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StepCountServiceImpl implements StepCountService {

    private final StepCountRepository stepCountRepository;
    private final UserRepository userRepository;

    private final StepCountMapper stepCountMapper;

    @Override
    @Transactional
    public List<StepCount> storeData(Long userId, List<StepCountRequest> stepCountRequests) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        List<StepCount> entities = stepCountRequests.stream()
                .map(stepCountMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return stepCountRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public List<StepCountResponse> getBetween(Long userId, Long startTime, Long endTime) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<StepCount> entities = stepCountRepository.findByUserAndTimestampBetween(monitored, startTime, endTime);

        return entities.stream().map(stepCountMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<StepCountResponse> getRecent(Long userId, Long time) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<StepCount> entities = stepCountRepository.findByUserAndTimestampGreaterThan(monitored, time);

        return entities.stream().map(stepCountMapper::toDto).toList();
    }
}
