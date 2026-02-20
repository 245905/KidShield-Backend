package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.SigMotionRequest;
import com.dominik.control.kidshieldbackend.dto.SigMotionResponse;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.SigMotionMapper;
import com.dominik.control.kidshieldbackend.model.SigMotion;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.SigMotionRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.SigMotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SigMotionServiceImpl implements SigMotionService {

    private final SigMotionRepository sigMotionRepository;
    private final UserRepository userRepository;

    private final SigMotionMapper sigMotionMapper;

    @Override
    @Transactional
    public List<SigMotion> storeData(Long userId, List<SigMotionRequest> sigMotionRequests) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        List<SigMotion> entities = sigMotionRequests.stream()
                .map(sigMotionMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return sigMotionRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public List<SigMotionResponse> getBetween(Long userId, Long startTime, Long endTime) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<SigMotion> entities = sigMotionRepository.findByUserAndTimestampBetween(monitored, startTime, endTime);

        return entities.stream().map(sigMotionMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<SigMotionResponse> getRecent(Long userId, Long time) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<SigMotion> entities = sigMotionRepository.findByUserAndTimestampGreaterThan(monitored, time);

        return entities.stream().map(sigMotionMapper::toDto).toList();
    }
}
