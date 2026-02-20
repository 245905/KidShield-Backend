package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.UsageStatsRequest;
import com.dominik.control.kidshieldbackend.dto.UsageStatsResponse;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.mapper.UsageStatsMapper;
import com.dominik.control.kidshieldbackend.model.UsageStats;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.UsageStatsRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.UsageStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsageStatsServiceImpl implements UsageStatsService {

    private final UsageStatsRepository usageStatsRepository;
    private final UserRepository userRepository;

    private final UsageStatsMapper usageStatsMapper;

    @Override
    @Transactional
    public List<UsageStats> storeData(Long userId, List<UsageStatsRequest> usageStatsRequests) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        Date date = usageStatsRequests.get(0).getDate();
        usageStatsRepository.deleteAllByUserAndDate(user, date);

        List<UsageStats> entities = usageStatsRequests.stream()
                .map(usageStatsMapper::toEntity)
                .peek(entity -> entity.setUser(user))
                .toList();

        return usageStatsRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public List<UsageStatsResponse> getByDate(Long userId, Date date) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<UsageStats> entities = usageStatsRepository.findByUserAndDate(monitored, date);

        return entities.stream().map(usageStatsMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<UsageStatsResponse> getByDateAndName(Long userId, Date date, String name) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<UsageStats> entities = usageStatsRepository.findByUserAndDateAndPackageName(monitored, date, name);

        return entities.stream().map(usageStatsMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<UsageStatsResponse> getByName(Long userId, String name) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        User monitored = userRepository.findBySupervisorsContaining(user).orElseThrow(
                () -> new UserNotFoundException("Monitored user not found for user with id: " + userId)
        );

        List<UsageStats> entities = usageStatsRepository.findByUserAndPackageName(monitored, name);

        return entities.stream().map(usageStatsMapper::toDto).toList();
    }
}
