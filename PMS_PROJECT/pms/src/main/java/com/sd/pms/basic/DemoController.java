package com.sd.pms.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Object> validateToken(){
        log.info("AUTHENTICATED");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
