package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.AppInfoDiff;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInfoDiffRepository extends JpaRepository<AppInfoDiff, Long> {

    @Query("SELECT ai FROM AppInfoDiff ai WHERE ai.user = :user AND ai.referenceNumber = " +
            "(SELECT MAX(ai2.referenceNumber) FROM AppInfoDiff ai2 WHERE ai2.user = :user)")
    List<AppInfoDiff> findAllByUserWithMaxReferenceNumber(@Param("user") User user);
}
