package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.HourlyStatsMapper;
import com.dominik.control.kidshieldbackend.model.HourlyStats;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.HourlyStatsRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.HourlyStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HourlyStatsServiceImpl implements HourlyStatsService {

    private final HourlyStatsRepository hourlyStatsRepository;
    private final UserRepository userRepository;

    private final HourlyStatsMapper hourlyStatsMapper;

    @Override
    @Transactional
    public List<HourlyStats> storeData(Long userId, List<HourlyStatsRequest> hourlyStatsRequests) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        List<HourlyStats> entities = hourlyStatsRequests.stream()
                .map(hourlyStatsMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return hourlyStatsRepository.saveAll(entities);
    }
}
