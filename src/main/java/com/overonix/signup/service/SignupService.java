package com.overonix.signup.service;

import com.overonix.signup.model.BaseResponse;
import com.overonix.signup.model.SignupRequest;
import com.overonix.signup.model.SignupResponse;

import java.util.Optional;

public interface SignupService {
    BaseResponse publish(SignupRequest request);
    SignupResponse queryByUuid(String Uuid);

}
