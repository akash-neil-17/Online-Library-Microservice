package com.bjit.authentication_service.service.Implementation;

import com.bjit.authentication_service.entity.Role;
import com.bjit.authentication_service.entity.UserEntity;
import com.bjit.authentication_service.exception.UserNotFoundException;
import com.bjit.authentication_service.model.AuthenticationRequest;
import com.bjit.authentication_service.model.AuthenticationResponse;
import com.bjit.authentication_service.model.UserRequestModel;
import com.bjit.authentication_service.repository.UserRepository;
import com.bjit.authentication_service.service.UserService;
import com.bjit.authentication_service.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Object> register(UserRequestModel userRequest) {
        String userName = userRequest.getName();
        String userEmail = userRequest.getEmail();

        UserEntity userEntity = UserEntity.builder().email(userEmail).name(userName).password(passwordEncoder.encode(userRequest.getPassword())).role(Objects.equals(userRequest.getRole(), "ADMIN") ? Role.ADMIN : Role.USER).build();
        userRepository.save(userEntity);
        return new ResponseEntity<>("User is created!", HttpStatus.CREATED);
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByEmail(authenticationRequest.getEmail());
        if(user==null){
            throw new UserNotFoundException("User not found");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
