package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.StepCount;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepCountRepository extends JpaRepository<StepCount, Long> {
    List<StepCount> findByUser(User user);
    List<StepCount> findByUserAndTimestampGreaterThan(User user, Long time);
    List<StepCount> findByUserAndTimestampBetween(User user, Long startTime, Long endTime);
}
