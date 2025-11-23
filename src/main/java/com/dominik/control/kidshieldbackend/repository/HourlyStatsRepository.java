package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.HourlyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourlyStatsRepository extends JpaRepository<HourlyStats, Long> {
}
