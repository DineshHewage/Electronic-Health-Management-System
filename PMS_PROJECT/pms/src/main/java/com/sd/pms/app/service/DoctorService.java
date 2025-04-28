package com.sd.pms.app.service;

import com.sd.pms.app.dto.CreateDoctorDto;
import com.sd.pms.app.entity.Doctor;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.dto.UpdateDoctorDto;

import java.util.List;

public interface DoctorService {
    Doctor createDoctor(CreateDoctorDto dto);
    Doctor updateDoctor(Long doctorId , UpdateDoctorDto updateDoctorDto) throws HttpErrorException;
    void deleteDoctor(Long doctorId) throws HttpErrorException;
    List<Doctor> getAllDoctors();
    Doctor getDoctor(Long id) throws HttpErrorException;
}
