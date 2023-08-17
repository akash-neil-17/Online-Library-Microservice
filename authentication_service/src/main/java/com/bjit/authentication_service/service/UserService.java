package com.bjit.authentication_service.service;

import com.bjit.authentication_service.entity.UserEntity;
import com.bjit.authentication_service.model.AuthenticationRequest;
import com.bjit.authentication_service.model.AuthenticationResponse;
import com.bjit.authentication_service.model.UserRequestModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> register(UserRequestModel requestModel);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    UserEntity findByEmail(String email);
}
