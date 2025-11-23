package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.UsageStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageStatsRepository extends JpaRepository<UsageStats, Long> {
}
