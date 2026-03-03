package com.dominik.control.kidshieldbackend.service;

import com.dominik.control.kidshieldbackend.dto.*;

public interface PairingCodeService {

    GenerateCodeResponse generateCode(Long userId);
    void pairByUUID(PairByUUIDRequest request, Long userId);
    void pairByPin(PairByPinRequest request, Long userId);
    CheckPairStatusResponse checkPairStatus(CheckPairStatusRequest request, Long userId);
}
