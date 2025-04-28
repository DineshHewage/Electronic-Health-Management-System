package com.sd.pms.app.controller;

import com.sd.pms.app.entity.Patient;
import com.sd.pms.app.exceptions.FieldValidationException;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.service.PatientService;
import com.sd.pms.app.dto.CreatePatientDto;
import com.sd.pms.app.dto.UpdatePatientDto;
import com.sd.pms.app.util.EmailValidationUtil;
import com.sd.pms.app.util.PasswordValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(@RequestBody CreatePatientDto dto) throws FieldValidationException, HttpErrorException {

        if (dto.getFirstName() == null || dto.getFirstName().equals("")) {
            throw new FieldValidationException("First name is required!");
        }

        if (dto.getLastName() == null || dto.getLastName().equals("")) {
            throw new FieldValidationException("Last name is required!");
        }

        if (dto.getEmail() == null || dto.getEmail().equals("")) {
            throw new FieldValidationException("Email is required!");
        }

        if (!EmailValidationUtil.isValidEmail(dto.getEmail())) {
            throw new FieldValidationException("Invalid email format");
        }

        if (dto.getPassword() == null || dto.getPassword().equals("")) {
            throw new FieldValidationException("Password is required");
        }

        if (!PasswordValidatorUtil.isValidPassword(dto.getPassword())) {
            throw new FieldValidationException("Invalid password format");
        }

        if (dto.getNic() == null || dto.getNic().equals("")) {
            throw new FieldValidationException("NIC number is required");
        }

        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().equals("")) {
            throw new FieldValidationException("Phone number is required");
        }

        if (dto.getDob() == null) {
            throw new FieldValidationException("DOB name is required");
        }

        if (dto.getBloodGroup() == null || dto.getBloodGroup().equals("")) {
            throw new FieldValidationException("Blood group is required");
        }


        patientService.createPatient(dto);
        dto.setPassword("");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PutMapping("/updatePatient")
    private ResponseEntity<?> updatePatient(@RequestParam("patientId") Long id , @RequestBody UpdatePatientDto dto) throws FieldValidationException, HttpErrorException {

        if (id == null) {
            throw new FieldValidationException("Please give doctorid");
        }

        if (dto.getFirstName() == null || dto.getFirstName().equals("")) {
            throw new FieldValidationException("First name is required!");
        }

        if (dto.getLastName() == null || dto.getLastName().equals("")) {
            throw new FieldValidationException("Last name is required!");
        }

        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().equals("")) {
            throw new FieldValidationException("Phone number is required");
        }

        if (dto.getNic() == null || dto.getNic().equals("")) {
            throw new FieldValidationException("NIC number is required");
        }

        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().equals("")) {
            throw new FieldValidationException("Phone number is required");
        }

        if (dto.getDob() == null) {
            throw new FieldValidationException("DOB name is required");
        }

        if (dto.getBloodGroup() == null || dto.getBloodGroup().equals("")) {
            throw new FieldValidationException("Blood group is required");
        }

        patientService.updatePatient(id, dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient")
    public ResponseEntity<?> deletePatient(@RequestParam("patientId") Long id) throws FieldValidationException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Please give patient id");
        }

        patientService.deletePatient(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatient(){
        ArrayList<Patient> patients = (ArrayList<Patient>) patientService.getAllPatient();
        return new ResponseEntity<>(patients , HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getPatient(@RequestParam("patientId") Long id) throws HttpErrorException {
        Patient patient = patientService.getPatient(id);
        return new ResponseEntity<>(patient , HttpStatus.OK);
    }

}
