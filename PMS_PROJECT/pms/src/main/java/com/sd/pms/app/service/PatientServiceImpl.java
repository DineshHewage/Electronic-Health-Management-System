package com.sd.pms.app.service;

import com.sd.pms.app.entity.Patient;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.PatientRepository;
import com.sd.pms.auth.user.*;
import com.sd.pms.app.dto.CreatePatientDto;
import com.sd.pms.app.dto.UpdatePatientDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;

    @Override
    public Patient createPatient(CreatePatientDto dto) throws HttpErrorException {
        Role role = roleRepository.findById(RoleType.USER.getDisplayText()).orElseThrow(()->new HttpErrorException("Role not found"));

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

        // create doctor
        Patient patient = Patient.builder()
                .user(user)
                .nic(dto.getNic())
                .phoneNumber(dto.getPhoneNumber())
                .dob(dto.getDob())
                .bloodGroup(dto.getBloodGroup())
                .active(true)
                .build();

        log.info("save patient : {}" , patient.toString());
        patientRepository.save(patient);

        return patient;
    }

    @Override
    public Patient updatePatient(Long patientId, UpdatePatientDto updatePatientDto) throws HttpErrorException {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new HttpErrorException("Patient not found"));

        log.info("patient object before update : {}" , patient.toString());

        patient.getUser().setFirstName(updatePatientDto.getFirstName());
        patient.getUser().setLastName(updatePatientDto.getLastName());
        patient.setPhoneNumber(updatePatientDto.getPhoneNumber());
        patient.setNic(updatePatientDto.getNic());
        patient.setDob(updatePatientDto.getDob());
        patient.setBloodGroup(updatePatientDto.getBloodGroup());

        log.info("patient object after update : {}" , patient.toString());
        patientRepository.save(patient);

        return patient;
    }

    @Transactional
    @Override
    public void deletePatient(Long patientId) throws HttpErrorException {
        log.info("delete patient id : {}" , patientId);
        Patient deletedToBePatient = patientRepository.findById(patientId).orElseThrow(()->new HttpErrorException("Patient not found"));
        log.info("delete patient id : {}" , deletedToBePatient.getUser().toString());
        deletedToBePatient.setActive(false);
        deletedToBePatient.getUser().setActive(false);
        patientRepository.save(deletedToBePatient);
    }

    @Override
    public List<Patient> getAllPatient() {
        log.info("get all patients");
        return patientRepository.findAllByActive(Boolean.TRUE);
    }

    @Override
    public Patient getPatient(Long id) throws HttpErrorException {
        log.info("get patient by if {}" , id);
        return patientRepository.findById(id).orElseThrow(()->new HttpErrorException("Patient not found"));
    }
}
