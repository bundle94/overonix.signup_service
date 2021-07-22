package com.overonix.signup.service;

import com.overonix.signup.model.BaseResponse;
import com.overonix.signup.model.SignupRequest;

public interface SignupService {
    BaseResponse publish(SignupRequest request);
}
