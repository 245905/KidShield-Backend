package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.GenerateCodeResponse;
import com.dominik.control.kidshieldbackend.dto.PairByPinRequest;
import com.dominik.control.kidshieldbackend.dto.PairByUUIDRequest;

public interface PairingCodeService {

    GenerateCodeResponse generateCode(Long userId);
    void pairByUUID(PairByUUIDRequest request, Long userId);
    void pairByPin(PairByPinRequest request, Long userId);
}
