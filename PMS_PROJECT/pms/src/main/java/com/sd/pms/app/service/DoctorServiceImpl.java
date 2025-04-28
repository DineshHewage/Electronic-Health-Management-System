package com.sd.pms.app.service;

import com.sd.pms.app.dto.CreateDoctorDto;
import com.sd.pms.app.dto.UpdateDoctorDto;
import com.sd.pms.app.entity.Doctor;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.DoctorRepository;
import com.sd.pms.auth.user.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;

    @Override
    public Doctor createDoctor(CreateDoctorDto dto) {

        Role role = roleRepository.getById(RoleType.DOCTOR.getDisplayText());

        // create user
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .active(Boolean.TRUE)
                .build();

        log.info("save user : {}" , user.toString());
        userRepository.save(user);

        // create doctor
        Doctor doctor = Doctor.builder()
                .user(user)
                .registrationNumber(dto.getRegistrationNumber())
                .phoneNumber(dto.getPhoneNumber())
                .hospitalName(dto.getHospitalName())
                .active(Boolean.TRUE)
                .build();

        log.info("save doctor : {}" , doctor.toString());
        doctorRepository.save(doctor);

        return doctor;
    }

    @Override
    public Doctor updateDoctor(Long doctorId , UpdateDoctorDto updateDoctorDto) throws HttpErrorException {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new HttpErrorException("Doctor not found"));

        log.info("doctor object before update : {}" , doctor.toString());

        doctor.getUser().setFirstName(updateDoctorDto.getFirstName());
        doctor.getUser().setLastName(updateDoctorDto.getLastName());
        doctor.setRegistrationNumber(updateDoctorDto.getRegistrationNumber());
        doctor.setPhoneNumber(updateDoctorDto.getPhoneNumber());
        doctor.setHospitalName(updateDoctorDto.getHospitalName());

        log.info("doctor object after update : {}" , doctor.toString());
        doctorRepository.save(doctor);

        return doctor;
    }

    @Transactional
    @Override
    public void deleteDoctor(Long doctorId) throws HttpErrorException {
        log.info("delete doctor id : {}" , doctorId);
        Doctor deleteToBeDoctor = doctorRepository.findById(doctorId).orElseThrow(()->new HttpErrorException("Doctor not found"));
        log.info("delete user id : {}" , deleteToBeDoctor.getUser().toString());
        deleteToBeDoctor.setActive(false);
        deleteToBeDoctor.getUser().setActive(false);
        doctorRepository.save(deleteToBeDoctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        log.info("get all users");
        return doctorRepository.findAllByActive(Boolean.TRUE);
    }

    @Override
    public Doctor getDoctor(Long id) throws HttpErrorException {
        log.info("get all users");
        return doctorRepository.findById(id).orElseThrow(()->new HttpErrorException("Doctor not found"));
    }
}
