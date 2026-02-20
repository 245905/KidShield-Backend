package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.PointRequest;
import com.dominik.control.kidshieldbackend.dto.PointResponse;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.PointMapper;
import com.dominik.control.kidshieldbackend.model.Point;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.PointRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    private final PointMapper pointMapper;

    @Override
    @Transactional
    public List<Point> storeData(Long userId, List<PointRequest> pointRequests) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        List<Point> entities = pointRequests.stream()
                .map(pointMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return pointRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public List<PointResponse> getBetween(Long userId, Long startTime, Long endTime) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<Point> entities = pointRepository.findByUserAndReceivedTimeBetween(monitored, startTime, endTime);

        return entities.stream().map(pointMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<PointResponse> getRecent(Long userId, Long time) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<Point> entities = pointRepository.findByUserAndReceivedTimeGreaterThan(monitored, time);

        return entities.stream().map(pointMapper::toDto).toList();
    }
}
