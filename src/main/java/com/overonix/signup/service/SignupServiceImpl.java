package com.overonix.signup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overonix.signup.model.BaseResponse;
import com.overonix.signup.model.SignupRequest;
import com.overonix.signup.model.SignupResponse;
import com.overonix.signup.repository.PlayerEntity;
import com.overonix.signup.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SignupServiceImpl implements SignupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupServiceImpl.class);
    private PasswordEncoder passwordEncoder;
    private PlayerRepository playerRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SignupServiceImpl(PasswordEncoder passwordEncoder, PlayerRepository playerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.playerRepository = playerRepository;
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
            response.setUuid(uuid);
        }
        catch (Exception e) {
            LOGGER.error("Cannot publish message", e.getMessage());
        }
        return response;
    }

    @Override
    public SignupResponse queryByUuid(String uuid) {
        SignupResponse res = new SignupResponse();
        Optional<PlayerEntity> response = playerRepository.findByUuid(uuid);
        if(response.isEmpty()) {
            res.setMessage("No player record found");
        }
        else {
            res.setUuid(UUID.fromString(response.get().getUuid()));
            res.setEmail(response.get().getEmail());
            res.setPassword(response.get().getPassword());
        }
        return res;
    }
}
