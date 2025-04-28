package com.sd.pms.app.service;

import com.sd.pms.app.entity.Patient;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.dto.CreatePatientDto;
import com.sd.pms.app.dto.UpdatePatientDto;

import java.util.List;

public interface PatientService {
    Patient createPatient(CreatePatientDto dto) throws HttpErrorException;
    Patient updatePatient(Long patientId , UpdatePatientDto updatePatientDto) throws HttpErrorException;
    void deletePatient(Long patientId) throws HttpErrorException;
    List<Patient> getAllPatient();
    Patient getPatient(Long id) throws HttpErrorException;
}
