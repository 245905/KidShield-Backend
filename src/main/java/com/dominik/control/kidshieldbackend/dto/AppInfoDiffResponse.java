package com.dominik.control.kidshieldbackend.dto;

import com.dominik.control.kidshieldbackend.model.AppChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppInfoDiffResponse {

    private Long referenceNumber;

    private AppChangeType changeType;

    private String appName;
    private String packageName;
    private String oldVersionName;
    private Long oldVersionCode;
    private Long lastUpdateTime;
}
