package com.sd.pms.app.controller;

import com.sd.pms.app.dto.CreateDoctorDto;
import com.sd.pms.app.dto.UpdateDoctorDto;
import com.sd.pms.app.entity.Doctor;
import com.sd.pms.app.exceptions.FieldValidationException;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.service.DoctorService;
import com.sd.pms.app.util.EmailValidationUtil;
import com.sd.pms.app.util.PasswordValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(@RequestBody CreateDoctorDto createDoctorDto) throws FieldValidationException {

        if (createDoctorDto.getFirstName() == null || createDoctorDto.getFirstName().equals("")) {
            throw new FieldValidationException("First name is required!");
        }

        if (createDoctorDto.getLastName() == null || createDoctorDto.getLastName().equals("")) {
            throw new FieldValidationException("Last name is required!");
        }

        if (createDoctorDto.getEmail() == null || createDoctorDto.getEmail().equals("")) {
            throw new FieldValidationException("Email is required!");
        }

        if (!EmailValidationUtil.isValidEmail(createDoctorDto.getEmail())) {
            throw new FieldValidationException("Invalid email format");
        }

        if (createDoctorDto.getPassword() == null || createDoctorDto.getPassword().equals("")) {
            throw new FieldValidationException("Password is required");
        }

        if (!PasswordValidatorUtil.isValidPassword(createDoctorDto.getPassword())) {
            throw new FieldValidationException("Invalid password format");
        }

        if (createDoctorDto.getRegistrationNumber() == null || createDoctorDto.getRegistrationNumber().equals("")) {
            throw new FieldValidationException("Registration number is required");
        }

        if (createDoctorDto.getPhoneNumber() == null || createDoctorDto.getPhoneNumber().equals("")) {
            throw new FieldValidationException("Phone number is required");
        }

        if (createDoctorDto.getHospitalName() == null || createDoctorDto.getHospitalName().equals("")) {
            throw new FieldValidationException("Hospital name is required");
        }

        doctorService.createDoctor(createDoctorDto);
        createDoctorDto.setPassword("");

        return new ResponseEntity<>(createDoctorDto, HttpStatus.CREATED);

    }

    @PutMapping("/updateDoctor")
    private ResponseEntity<?> updateDoctor(@RequestParam("doctorId") Long id , @RequestBody UpdateDoctorDto dto) throws FieldValidationException, HttpErrorException {

        if (id == null) {
            throw new FieldValidationException("Please give doctorid");
        }

        if (dto.getFirstName() == null || dto.getFirstName().equals("")) {
            throw new FieldValidationException("First name is required!");
        }

        if (dto.getLastName() == null || dto.getLastName().equals("")) {
            throw new FieldValidationException("Last name is required!");
        }

        if (dto.getRegistrationNumber() == null || dto.getRegistrationNumber().equals("")) {
            throw new FieldValidationException("Registration number is required");
        }

        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().equals("")) {
            throw new FieldValidationException("Phone number is required");
        }

        if (dto.getHospitalName() == null || dto.getHospitalName().equals("")) {
            throw new FieldValidationException("Hospital name is required");
        }

        doctorService.updateDoctor(id, dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDoctor")
    public ResponseEntity<?> deleteDoctor(@RequestParam("doctorId") Long id) throws FieldValidationException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Please give doctor id");
        }

        doctorService.deleteDoctor(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("")
    public Doctor getDoctor(@RequestParam("doctorId") Long id) throws HttpErrorException {
        return doctorService.getDoctor(id);
    }
}
