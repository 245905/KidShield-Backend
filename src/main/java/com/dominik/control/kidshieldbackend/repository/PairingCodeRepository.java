package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.PairingCode;
import com.dominik.control.kidshieldbackend.model.PairingCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PairingCodeRepository extends JpaRepository<PairingCode, UUID> {
    Boolean existsByPinAndStatusAndExpiresAtAfter(String pin, PairingCodeStatus status, LocalDateTime time);
    Optional<PairingCode> findByPin(String pin);
}
