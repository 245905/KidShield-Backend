package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.UsageStats;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UsageStatsRepository extends JpaRepository<UsageStats, Long> {

    void deleteAllByUserAndDate(User user, Date date);
    List<UsageStats> findByUserAndDateAndPackageName(User user, Date date, String packageName);
    List<UsageStats> findByUserAndDate(User user, Date date);
    List<UsageStats> findByUserAndPackageName(User user, String packageName);

}
