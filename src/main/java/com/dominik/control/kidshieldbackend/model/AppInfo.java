package com.dominik.control.kidshieldbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_infos")
public class AppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long referenceNumber;

    @Column(nullable = false)
    private String appName;
    @Column(nullable = false)
    private String packageName;
    private String versionName;
    @Column(nullable = false)
    private Long versionCode;
    @Column(nullable = false)
    private Long firstInstallTime;
    @Column(nullable = false)
    private Long lastUpdateTime;
    @Column(nullable = false)
    private Boolean isSystemApp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
