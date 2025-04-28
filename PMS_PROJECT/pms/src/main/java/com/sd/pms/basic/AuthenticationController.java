package com.sd.pms.basic;

import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.auth.AuthenticationService;
import com.sd.pms.auth.dto.AuthenticationRequest;
import com.sd.pms.auth.dto.AuthenticationResponse;
import com.sd.pms.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws HttpErrorException {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Object> validateToken(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
