package com.dominik.control.kidshieldbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"monitored"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pairing_codes")
public class PairingCode {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String pin;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PairingCodeStatus status = PairingCodeStatus.UNUSED;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User monitored;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
