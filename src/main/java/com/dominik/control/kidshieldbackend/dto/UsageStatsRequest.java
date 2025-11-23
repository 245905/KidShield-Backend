package com.dominik.control.kidshieldbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsageStatsRequest {

    private Date date;
    private String appName;
    private String packageName;
    private Boolean isSystemApp;

    private Long lastTimeUsed;

    private Long totalTimeInForeground; // main data
    private Long totalTimeVisible;
}
