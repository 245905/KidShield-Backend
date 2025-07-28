package com.dominik.control.kidshieldbackend.service.impl;

import com.dominik.control.kidshieldbackend.exception.RefreshTokenAlreadyRevokedException;
import com.dominik.control.kidshieldbackend.exception.RefreshTokenExpiredException;
import com.dominik.control.kidshieldbackend.exception.RefreshTokenNotFoundException;
import com.dominik.control.kidshieldbackend.exception.UserNotFoundException;
import com.dominik.control.kidshieldbackend.model.RefreshToken;
import com.dominik.control.kidshieldbackend.model.User;
import com.dominik.control.kidshieldbackend.repository.RefreshTokenRepository;
import com.dominik.control.kidshieldbackend.repository.UserRepository;
import com.dominik.control.kidshieldbackend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.expiration-ms}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UUID tokenFamily = UUID.randomUUID();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .family(tokenFamily)
                .expiresAt(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenDurationMs)))
                .token(UUID.randomUUID())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Verifies and rotates a refresh token.
     * Implements reuse detection.
     */
    @Override
    @Transactional
    public RefreshToken rotateRefreshToken(UUID requestToken) {
        RefreshToken oldToken = refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException(
                        "Refresh token not found with uuid: " + requestToken
                ));

        if(oldToken.getIsRevoked()){
            refreshTokenRepository.revokeAllByFamily(oldToken.getFamily());
            throw new RefreshTokenAlreadyRevokedException("Refresh token has already been used. All sessions for this user have been invalidated.");
        }

        oldToken.setIsRevoked(true);
        refreshTokenRepository.save(oldToken);

        if(oldToken.getExpiresAt().isBefore(LocalDateTime.now())){
            refreshTokenRepository.revokeAllByFamily(oldToken.getFamily());
            throw new RefreshTokenExpiredException("Refresh token family has expired.");
        }

        RefreshToken newToken = RefreshToken.builder()
                .user(oldToken.getUser())
                .family(oldToken.getFamily())
                .token(UUID.randomUUID())
                .expiresAt(LocalDateTime.now().plusMinutes(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(newToken);
    }

    /**
     * Deletes all refresh tokens for a given user.
     * This logs the user out on a single device.
     */
    @Override
    @Transactional
    public void logout(UUID requestToken) {
        refreshTokenRepository.findByTokenAndIsRevokedFalse(requestToken)
                .ifPresent(token -> refreshTokenRepository.revokeAllByFamily(token.getFamily()));
    }

    /**
     * Deletes all refresh tokens for a given user.
     * This logs the user out of all devices.
     */
    @Override
    @Transactional
    public void logoutAll(UUID requestToken) {
        refreshTokenRepository.findByTokenAndIsRevokedFalse(requestToken)
                .ifPresent(token -> refreshTokenRepository.revokeAllByUser(token.getUser()));
    }
}
