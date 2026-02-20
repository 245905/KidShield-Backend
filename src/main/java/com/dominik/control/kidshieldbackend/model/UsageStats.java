package com.dominik.control.kidshieldbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usage_stats")
public class UsageStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private Long lastTimeUsed;

    @Column(nullable = false)
    private Long totalTimeInForeground; // main data
    @Column(nullable = false)
    private Long totalTimeVisible;

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
