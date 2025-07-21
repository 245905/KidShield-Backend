package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.RegisterRequest;
import com.dominik.control.kidshieldbackend.model.User;

public interface UserService {
    User createUser(RegisterRequest registerRequest);
}
