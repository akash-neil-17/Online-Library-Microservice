package com.bjit.authentication_service.controller;

import com.bjit.authentication_service.model.AuthenticationRequest;
import com.bjit.authentication_service.model.AuthenticationResponse;
import com.bjit.authentication_service.model.UserRequestModel;
import com.bjit.authentication_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequestModel requestModel) {
        return userService.register(requestModel);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.OK);
    }
}
