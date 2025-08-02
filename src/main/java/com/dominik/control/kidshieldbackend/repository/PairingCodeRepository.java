package com.dominik.control.kidshieldbackend.repository;

import com.dominik.control.kidshieldbackend.model.PairingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PairingCodeRepository extends JpaRepository<PairingCode, UUID> {
}
