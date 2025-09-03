package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.dto.GenerateCodeResponse;
import com.dominik.control.kidshieldbackend.dto.PairByPinRequest;
import com.dominik.control.kidshieldbackend.dto.PairByUUIDRequest;
import com.dominik.control.kidshieldbackend.exception.PairingCodeExpiredException;
import com.dominik.control.kidshieldbackend.exception.PairingCodeNotFoundException;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.model.PairingCode;
import com.dominik.control.kidshieldbackend.model.PairingCodeStatus;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.PairingCodeRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.PairingCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PairingCodeServiceImpl implements PairingCodeService {

    @Value("${pairing.code.expiration-ms}")
    private Long pairingCodeDurationMs;

    private final PairingCodeRepository pairingCodeRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    @Override
    @Transactional
    public GenerateCodeResponse generateCode(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        String pin = null;
        int maxRetries = 10;
        for (int i = 0; i < maxRetries; i++) {
            String potentialPin = generatePin();

            boolean exists = pairingCodeRepository.existsByPinAndStatusAndExpiresAtAfter(
                    potentialPin,
                    PairingCodeStatus.UNUSED,
                    LocalDateTime.now(clock)
            );
            if (!exists) {
                pin = potentialPin;
                break;
            }
        }

        if (pin == null) {
            throw new IllegalStateException(
                    "Could not generate a unique pairing PIN after " + maxRetries + " attempts."
            );
        }

        PairingCode code = PairingCode.builder()
                .id(UUID.randomUUID())
                .pin(pin)
                .monitored(user)
                .expiresAt(LocalDateTime.now(clock).plus(Duration.ofMillis(pairingCodeDurationMs)))
                .build();

        code = pairingCodeRepository.save(code);

        return GenerateCodeResponse.builder()
                .id(code.getId())
                .pin(code.getPin())
                .build();
    }

    @Override
    @Transactional
    public void pairByUUID(PairByUUIDRequest request, Long userId) {
        User supervisor = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        PairingCode pairingCode = pairingCodeRepository.findById(request.getId()).orElseThrow(
                () -> new PairingCodeNotFoundException("Pairing code not found with id: " + request.getId())
        );

        performPairing(pairingCode, supervisor);

        pairingCode.setStatus(PairingCodeStatus.PAIRED_BY_UUID);
        pairingCodeRepository.save(pairingCode);
    }

    @Override
    @Transactional
    public void pairByPin(PairByPinRequest request, Long userId) {
        User supervisor = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );

        PairingCode pairingCode = pairingCodeRepository.findByPin(request.getPin()).orElseThrow(
                () -> new PairingCodeNotFoundException("Pairing code not found with pin: " + request.getPin())
        );

        performPairing(pairingCode, supervisor);

        pairingCode.setStatus(PairingCodeStatus.PAIRED_BY_PIN);
        pairingCodeRepository.save(pairingCode);
    }

    private void performPairing(PairingCode pairingCode, User supervisor) {
        if (pairingCode.getStatus() != PairingCodeStatus.UNUSED) {
            throw new IllegalStateException("Pairing code has already been used.");
        }
        if (pairingCode.getExpiresAt().isBefore(LocalDateTime.now(clock))) {
            throw new PairingCodeExpiredException("Pairing code has expired.");
        }

        User monitored = pairingCode.getMonitored();

        if (supervisor.getId().equals(monitored.getId())) {
            throw new IllegalStateException("User cannot monitor themselves.");
        }

        supervisor.getMonitored().add(monitored);
    }

    private String generatePin(){
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(1_000_000);
        return String.format("%06d", number);
    }
}
