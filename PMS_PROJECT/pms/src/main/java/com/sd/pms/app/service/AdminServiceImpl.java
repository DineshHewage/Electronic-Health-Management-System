package com.sd.pms.app.service;

import com.sd.pms.app.dto.CreateAdminDto;
import com.sd.pms.app.dto.UpdateAdminDto;
import com.sd.pms.app.entity.Admin;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.AdminRepository;
import com.sd.pms.auth.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;


    @Override
    public Admin createAdmin(CreateAdminDto dto) throws HttpErrorException {
        Role role = roleRepository.findById(RoleType.DATA.getDisplayText()).orElseThrow(()->new HttpErrorException("Role not found"));

        // create user
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .active(true)
                .build();

        log.info("save user : {}" , user.toString());
        userRepository.save(user);

        // create admin
        Admin admin = Admin.builder()
                .user(user)
                .phoneNumber(dto.getPhoneNumber())
                .hospitalName(dto.getHospitalName())
                .nic(dto.getNic())
                .active(true)
                .build();

        log.info("save admin : {}" , admin.toString());
        adminRepository.save(admin);

        return admin;
    }

    @Override
    public Admin updateAdmin(Long adminId, UpdateAdminDto dto) throws HttpErrorException {
        Admin admin = adminRepository.findById(adminId).orElseThrow(()-> new HttpErrorException("Admin not found"));

        log.info("admin object before update : {}" , admin.toString());

        admin.getUser().setFirstName(dto.getFirstName());
        admin.getUser().setLastName(dto.getLastName());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setHospitalName(dto.getHospitalName());
        admin.setNic(dto.getNic());

        log.info("admin object after update : {}" , admin.toString());
        adminRepository.save(admin);

        return admin;
    }

    @Override
    public void deleteAdmin(Long adminId) throws HttpErrorException {
        log.info("delete admin id : {}" , adminId);
        Admin adminToBePatient = adminRepository.findById(adminId).orElseThrow(()->new HttpErrorException("Admin not found"));
        log.info("delete admin id : {}" , adminToBePatient.getUser().toString());
        adminToBePatient.setActive(false);
        adminToBePatient.getUser().setActive(false);
        adminRepository.save(adminToBePatient);
    }

    @Override
    public List<Admin> getAllAdmins() {
        log.info("get all patients");
        return adminRepository.findAllByActive(Boolean.TRUE);
    }

    @Override
    public Admin getAdmin(Long id) throws HttpErrorException {
        log.info("get patient by if {}" , id);
        return adminRepository.findById(id).orElseThrow(()->new HttpErrorException("Admin not found"));
    }
}
