package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.HourlyStats;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HourlyStatsRepository extends JpaRepository<HourlyStats, Long> {

    void deleteAllByUserAndDate(User user, Date date);
    List<HourlyStats> findByUserAndDateAndPackageName(User user, Date date, String packageName);
    List<HourlyStats> findByUserAndDate(User user, Date date);
    List<HourlyStats> findByUserAndPackageName(User user, String packageName);
}
