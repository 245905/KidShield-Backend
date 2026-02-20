package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.SigMotion;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SigMotionRepository extends JpaRepository<SigMotion, Long> {
    List<SigMotion> findByUser(User user);
    List<SigMotion> findByUserAndTimestampGreaterThan(User user, Long time);
    List<SigMotion> findByUserAndTimestampBetween(User user, Long startTime, Long endTime);
}
