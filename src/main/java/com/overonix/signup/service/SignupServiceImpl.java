package com.overonix.signup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overonix.signup.config.BCryptPasswordConfig;
import com.overonix.signup.model.BaseResponse;
import com.overonix.signup.model.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignupServiceImpl implements SignupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupServiceImpl.class);
    private PasswordEncoder passwordEncoder;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SignupServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseResponse publish(SignupRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            UUID uuid = UUID.randomUUID();
            request.setUuid(uuid);
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            ObjectMapper obj = new ObjectMapper();
            String message = obj.writeValueAsString(request);
            kafkaTemplate.send("overonix-distributed", message);
            response.setPublished(true);
            response.setUuid(uuid);
        }
        catch (Exception e) {
            LOGGER.error("Cannot publish message", e.getMessage());
        }
        return response;
    }
}
