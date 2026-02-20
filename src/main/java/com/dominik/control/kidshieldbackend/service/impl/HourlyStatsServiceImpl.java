package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.HourlyStatsRequest;
import com.dominik.control.kidshieldbackend.dto.HourlyStatsResponse;
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

import java.util.Date;
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

        Date date = hourlyStatsRequests.get(0).getDate();
        hourlyStatsRepository.deleteAllByUserAndDate(user, date);

        List<HourlyStats> entities = hourlyStatsRequests.stream()
                .map(hourlyStatsMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return hourlyStatsRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public List<HourlyStatsResponse> getByDate(Long userId, Date date) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<HourlyStats> entities = hourlyStatsRepository.findByUserAndDate(monitored, date);

        return entities.stream().map(hourlyStatsMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<HourlyStatsResponse> getByName(Long userId, String name) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<HourlyStats> entities = hourlyStatsRepository.findByUserAndPackageName(monitored, name);

        return entities.stream().map(hourlyStatsMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<HourlyStatsResponse> getByDateAndName(Long userId, Date date, String name) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<HourlyStats> entities = hourlyStatsRepository.findByUserAndDateAndPackageName(monitored, date, name);

        return entities.stream().map(hourlyStatsMapper::toDto).toList();
    }
}
