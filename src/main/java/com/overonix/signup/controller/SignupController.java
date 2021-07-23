package com.overonix.signup.controller;

import com.overonix.signup.model.BaseResponse;
import com.overonix.signup.model.SignupRequest;
import com.overonix.signup.model.SignupResponse;
import com.overonix.signup.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1")
public class SignupController {

    private SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @RequestMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> publish(@RequestBody @Validated SignupRequest request) {
        BaseResponse response = signupService.publish(request);
        if(response.getUuid()!= null) {
            return ResponseEntity.ok(response.getUuid());
        }
        return ResponseEntity.internalServerError().body("KO");
    }


    @RequestMapping(value = "/fetch/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SignupResponse> fetchByUuid(@PathVariable("uuid") String uuid) {
        SignupResponse response = signupService.queryByUuid(uuid);
        return ResponseEntity.ok(response);
    }
}
