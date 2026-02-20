package com.dominik.control.kidshieldbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppInfoResponse {

    private Long referenceNumber;

    private String appName;
    private String packageName;
    private String versionName;
    private Long versionCode;
    private Long firstInstallTime;
    private Long lastUpdateTime;
    private Boolean isSystemApp;
}
