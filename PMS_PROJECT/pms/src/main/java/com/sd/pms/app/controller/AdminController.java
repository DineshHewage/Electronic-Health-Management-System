package com.sd.pms.app.controller;

import com.sd.pms.app.dto.CreateAdminDto;
import com.sd.pms.app.dto.UpdateAdminDto;
import com.sd.pms.app.entity.Admin;
import com.sd.pms.app.exceptions.FieldValidationException;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.service.AdminService;
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
@RequestMapping("/api/v2/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminDto dto) throws FieldValidationException, HttpErrorException {

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

        if (dto.getHospitalName() == null || dto.getHospitalName().equals("")) {
            throw new FieldValidationException("Hospital name is required");
        }

        adminService.createAdmin(dto);
        dto.setPassword("");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PutMapping("/updateAdmin")
    private ResponseEntity<?> updateAdmin(@RequestParam("adminId") Long id , @RequestBody UpdateAdminDto dto) throws FieldValidationException, HttpErrorException {

        if (id == null) {
            throw new FieldValidationException("Please give adminId");
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

        if (dto.getHospitalName() == null || dto.getHospitalName().equals("")) {
            throw new FieldValidationException("Hospital name is required");
        }

        adminService.updateAdmin(id, dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<?> deleteAdmin(@RequestParam("adminId") Long id) throws FieldValidationException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Please give admin id");
        }

        adminService.deleteAdmin(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmin(){
        ArrayList<Admin> admins = (ArrayList<Admin>) adminService.getAllAdmins();
        return new ResponseEntity<>(admins , HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAdmin(@RequestParam("adminId") Long id) throws HttpErrorException {
        Admin admin = adminService.getAdmin(id);
        return new ResponseEntity<>(admin , HttpStatus.OK);
    }

}
