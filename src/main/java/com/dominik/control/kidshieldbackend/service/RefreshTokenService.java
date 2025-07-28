package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);

    RefreshToken rotateRefreshToken(UUID requestToken);

    void logout(UUID requestToken);

    void logoutAll(UUID requestToken);
}
