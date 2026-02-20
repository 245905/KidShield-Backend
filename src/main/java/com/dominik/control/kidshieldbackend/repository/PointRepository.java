package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.Point;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByUser(User user);
    List<Point> findByUserAndReceivedTimeGreaterThan(User user, Long time);
    List<Point> findByUserAndReceivedTimeBetween(User user, Long startTime, Long endTime);
}
