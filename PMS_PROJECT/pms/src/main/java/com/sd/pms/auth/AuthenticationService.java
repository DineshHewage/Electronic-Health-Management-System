package com.sd.pms.auth;

import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.PatientRepository;
import com.sd.pms.auth.dto.AuthenticationRequest;
import com.sd.pms.auth.user.RoleRepository;
import com.sd.pms.auth.config.JwtService;
import com.sd.pms.auth.user.Role;
import com.sd.pms.auth.user.User;
import com.sd.pms.auth.user.UserRepository;
import com.sd.pms.auth.dto.AuthenticationResponse;
import com.sd.pms.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
    private final PatientRepository patientRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        // TODO this is stats role
        Role role = roleRepository.findByRole("USER").orElse(null);

        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws HttpErrorException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        String nic = "";

        if(user.getRole().getRole().equals("USER")){
            nic = patientRepository.findByUserId(user.getId()).orElseThrow(()->new HttpErrorException("User not found")).getNic();
        }

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().getRole())
                .nic(nic)
                .build();
    }
}
