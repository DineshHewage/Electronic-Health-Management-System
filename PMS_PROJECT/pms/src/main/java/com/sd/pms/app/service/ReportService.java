package com.sd.pms.app.service;

import com.sd.pms.app.dto.reports.*;
import com.sd.pms.app.entity.reports.*;
import com.sd.pms.app.exceptions.HttpErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface ReportService {
    Fbs addFbs(MultipartFile file , FbsDto dto) throws IOException, HttpErrorException;
    void removeFbs(Long id) throws HttpErrorException, IOException;
    ArrayList getFbsByPatientId(String id) throws HttpErrorException, IOException;
    Fbs getFbsById(Long id) throws HttpErrorException, IOException;
    ReportData getFbsReport(String nic) throws HttpErrorException, IOException;

    BloodPressure addBp(MultipartFile file , BloodPressureDto dto) throws IOException, HttpErrorException;
    void removeBp(Long id) throws HttpErrorException, IOException;
    ArrayList getBpByPatientId(String id) throws HttpErrorException, IOException;
    BloodPressure getBpById(Long id) throws HttpErrorException, IOException;
    ReportData getBpReport(String nic) throws HttpErrorException, IOException;

    DietPlan addDietPlan(MultipartFile file , DietDto dto) throws IOException, HttpErrorException;
    void removeDietPlan(Long id) throws HttpErrorException, IOException;
    ArrayList getDietByPatientId(String nic) throws HttpErrorException, IOException;
    DietPlan getDietById(Long id) throws HttpErrorException, IOException;
    ReportData getDietReport(String nic) throws HttpErrorException, IOException;

    WBmi addWBmi(MultipartFile file , WBmiDto dto) throws IOException, HttpErrorException;
    void removeWBmi(Long id) throws HttpErrorException, IOException;
    ArrayList getWBmiByPatientId(String id) throws HttpErrorException, IOException;
    WBmi getWBmiById(Long id) throws HttpErrorException, IOException;
    ReportData getWBmiReport(String nic) throws HttpErrorException, IOException;

    SerumCreatinine addSerumCreatinine(MultipartFile file , SerumCreatinineDto dto) throws IOException, HttpErrorException;
    void removeSerumCreatinine(Long id) throws HttpErrorException, IOException;
    ArrayList getSerumCreatinineByPatientId(String id) throws HttpErrorException, IOException;
    SerumCreatinine getSerumCreatinineById(Long id) throws HttpErrorException, IOException;
    ReportData getSerumCreatinineReport(String nic) throws HttpErrorException, IOException;

    Hba1c6 addHba1c6(MultipartFile file , Hba1c6Dto dto) throws IOException, HttpErrorException;
    void removeHba1c6(Long id) throws HttpErrorException, IOException;
    ArrayList getHba1c6ByPatientId(String id) throws HttpErrorException, IOException;
    Hba1c6 getHba1c6ById(Long id) throws HttpErrorException, IOException;
    ReportData getHba1c6Report(String nic) throws HttpErrorException, IOException;

    LipidProfile addLipidProfile(MultipartFile file , LipidProfileDto dto) throws IOException, HttpErrorException;
    void removeLipidProfile(Long id) throws HttpErrorException, IOException;
    ArrayList getLipidProfileByPatientId(String id) throws HttpErrorException, IOException;
    LipidProfile getLipidProfileById(Long id) throws HttpErrorException, IOException;
    ReportData getLipidProfileReport(String nic) throws HttpErrorException, IOException;

    AstAtl addAstAtl(MultipartFile file , AstAtlDto dto) throws IOException, HttpErrorException;
    void removeAstAtl(Long id) throws HttpErrorException, IOException;
    ArrayList getAstAtlByPatientId(String nic) throws HttpErrorException, IOException;
    AstAtl getAstAtlById(Long id) throws HttpErrorException, IOException;
    ReportData getAstAtlReport(String nic) throws HttpErrorException, IOException;

    String saveFile(MultipartFile file , Report report) throws IOException;
    public void deleteFileByName(String filePathToDelete) throws IOException;
}
