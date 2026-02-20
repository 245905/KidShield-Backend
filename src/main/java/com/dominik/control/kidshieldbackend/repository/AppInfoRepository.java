package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.AppInfo;
import com.dominik.control.kidshieldbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {

    @Query("SELECT ai FROM AppInfo ai WHERE ai.user = :user AND ai.referenceNumber = " +
            "(SELECT MAX(ai2.referenceNumber) FROM AppInfo ai2 WHERE ai2.user = :user)")
    List<AppInfo> findAllByUserWithMaxReferenceNumber(@Param("user") User user);

    @Query("SELECT MAX(referenceNumber) FROM AppInfo WHERE user = :user")
    Long findMaxReferenceNumber(@Param("user") User user);
}
