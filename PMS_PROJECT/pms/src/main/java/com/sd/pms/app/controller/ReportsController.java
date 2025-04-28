package com.sd.pms.app.controller;

import com.sd.pms.app.dto.reports.*;
import com.sd.pms.app.entity.reports.*;
import com.sd.pms.app.exceptions.FieldValidationException;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/reports")
public class ReportsController {

    private final ReportService reportService;

    @PostMapping("/addFbs")
    public ResponseEntity<?> addFbs(@ModelAttribute FbsDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getFbs() == null) {
            throw new FieldValidationException("FBS is required!");
        }

        Fbs fbs = reportService.addFbs(dto.getReport() , dto);

        return new ResponseEntity<>(fbs, HttpStatus.CREATED);

    }

    @PostMapping("/removeFbs")
    public ResponseEntity<?> removeFbs(@RequestParam("fbsId") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("FBS id is required!");
        }

        reportService.removeFbs(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllBy")
    public ResponseEntity<?> findAllFbs(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<Fbs> fbsList = reportService.getFbsByPatientId(nic);

        return new ResponseEntity<>(fbsList , HttpStatus.OK);
    }

    @GetMapping("/fbs")
    public ResponseEntity<?> findFbsById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        Fbs fbs = reportService.getFbsById(id);

        return new ResponseEntity<>(fbs , HttpStatus.OK);
    }

    @GetMapping("/fbsReport")
    public ResponseEntity<?> findFbsById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getFbsReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


    @PostMapping("/addBp")
    public ResponseEntity<?> addBp(@ModelAttribute BloodPressureDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getBp() == null) {
            throw new FieldValidationException("BP is required!");
        }

        BloodPressure fbs = reportService.addBp(dto.getReport() , dto);

        return new ResponseEntity<>(fbs, HttpStatus.CREATED);

    }

    @PostMapping("/removeBp")
    public ResponseEntity<?> removeBp(@RequestParam("bpId") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Bp id is required!");
        }

        reportService.removeBp(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllBpBy")
    public ResponseEntity<?> findAllBp(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<Fbs> fbsList = reportService.getBpByPatientId(nic);

        return new ResponseEntity<>(fbsList , HttpStatus.OK);
    }

    @GetMapping("/bp")
    public ResponseEntity<?> findBpById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        BloodPressure bloodPressure = reportService.getBpById(id);

        return new ResponseEntity<>(bloodPressure , HttpStatus.OK);
    }

    @GetMapping("/bpReport")
    public ResponseEntity<?> findBpById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getBpReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }

    @PostMapping("/addDiet")
    public ResponseEntity<?> addDiet(@ModelAttribute DietDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getStartDate() == null) {
            throw new FieldValidationException("Start date is required!");
        }

        if (dto.getEndDate() == null) {
            throw new FieldValidationException("End date is required!");
        }

        DietPlan dietPlan = reportService.addDietPlan(dto.getReport() , dto);

        return new ResponseEntity<>(dietPlan, HttpStatus.CREATED);

    }

    @PostMapping("/removeDiet")
    public ResponseEntity<?> removeDiet(@RequestParam("dietId") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Diet plan id is required!");
        }

        reportService.removeDietPlan(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/findAllDietBy")
    public ResponseEntity<?> findAllDiet(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<DietPlan> diet = reportService.getDietByPatientId(nic);

        return new ResponseEntity<>(diet , HttpStatus.OK);
    }

    @GetMapping("/diet")
    public ResponseEntity<?> findDietById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        DietPlan dietPlan = reportService.getDietById(id);

        return new ResponseEntity<>(dietPlan , HttpStatus.OK);
    }


    @PostMapping("/addWBmi")
    public ResponseEntity<?> addWBmi(@ModelAttribute WBmiDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getWeight() == null || dto.getWeight() <= 0) {
            throw new FieldValidationException("Weight is required!");
        }

        if (dto.getBmi() == null || dto.getBmi() <= 0) {
            throw new FieldValidationException("BMI is required!");
        }

        WBmi wBmi = reportService.addWBmi(dto.getReport() , dto);

        return new ResponseEntity<>(wBmi, HttpStatus.CREATED);

    }

    @PostMapping("/removeWBmi")
    public ResponseEntity<?> removeWBmi(@RequestParam("wbmiId") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("WBmi id is required!");
        }

        reportService.removeWBmi(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllWBmiBy")
    public ResponseEntity<?> findAllWBmi(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<Fbs> fbsList = reportService.getWBmiByPatientId(nic);

        return new ResponseEntity<>(fbsList , HttpStatus.OK);
    }

    @GetMapping("/wBmi")
    public ResponseEntity<?> findWBmiById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        WBmi wBmi = reportService.getWBmiById(id);

        return new ResponseEntity<>(wBmi , HttpStatus.OK);
    }

    @GetMapping("/wBmiReport")
    public ResponseEntity<?> findWBmiById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getWBmiReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


    @PostMapping("/addScr")
    public ResponseEntity<?> addSerumCreatinine(@ModelAttribute SerumCreatinineDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getSerumCreatinine() == null) {
            throw new FieldValidationException("Serum creatinine is required!");
        }

        SerumCreatinine serumCreatinine = reportService.addSerumCreatinine(dto.getReport() , dto);

        return new ResponseEntity<>(serumCreatinine, HttpStatus.CREATED);

    }

    @PostMapping("/removeScr")
    public ResponseEntity<?> removeSerumCreatinine(@RequestParam("scr") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Serum creatinine id is required!");
        }

        reportService.removeSerumCreatinine(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllScrBy")
    public ResponseEntity<?> findAllScr(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<SerumCreatinine> serumCreatinines = reportService.getSerumCreatinineByPatientId(nic);

        return new ResponseEntity<>(serumCreatinines , HttpStatus.OK);
    }

    @GetMapping("/scr")
    public ResponseEntity<?> findScrById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        SerumCreatinine serumCreatinine = reportService.getSerumCreatinineById(id);

        return new ResponseEntity<>(serumCreatinine , HttpStatus.OK);
    }

    @GetMapping("/scriReport")
    public ResponseEntity<?> findscrById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getSerumCreatinineReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


    @PostMapping("/addHba1c6")
    public ResponseEntity<?> addHba1c6(@ModelAttribute Hba1c6Dto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getHba1c6() == null) {
            throw new FieldValidationException("Hba1c6 is required!");
        }

        Hba1c6 hba1c6 = reportService.addHba1c6(dto.getReport() , dto);

        return new ResponseEntity<>(hba1c6, HttpStatus.CREATED);

    }

    @PostMapping("/removeHba1c6")
    public ResponseEntity<?> removeHba1c6(@RequestParam("hba1c6") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("Hba1c6 id is required!");
        }

        reportService.removeHba1c6(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllHba1c6By")
    public ResponseEntity<?> findAllHba1c6(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<Hba1c6> hba1c6s = reportService.getHba1c6ByPatientId(nic);

        return new ResponseEntity<>(hba1c6s , HttpStatus.OK);
    }

    @GetMapping("/hba1c6")
    public ResponseEntity<?> findHba1c6ById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        Hba1c6 hba1c6 = reportService.getHba1c6ById(id);

        return new ResponseEntity<>(hba1c6 , HttpStatus.OK);
    }

    @GetMapping("/Hba1c6Report")
    public ResponseEntity<?> findHba1c6ById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getHba1c6Report(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


    @PostMapping("/addLipidProfile")
    public ResponseEntity<?> addLipidProfile(@ModelAttribute LipidProfileDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getHdl() == null) {
            throw new FieldValidationException("HDL is required!");
        }

        if (dto.getLdl() == null) {
            throw new FieldValidationException("LDL is required!");
        }

        if (dto.getVldl() == null) {
            throw new FieldValidationException("VLDL is required!");
        }

        if (dto.getTag() == null) {
            throw new FieldValidationException("TAG is required!");
        }

        if (dto.getChdl() == null) {
            throw new FieldValidationException("CHDL is required!");
        }

        LipidProfile lipidProfile = reportService.addLipidProfile(dto.getReport() , dto);

        return new ResponseEntity<>(lipidProfile, HttpStatus.CREATED);

    }

    @PostMapping("/removeLipidProfile")
    public ResponseEntity<?> removeLipidProfile(@RequestParam("lipidProfile") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("LipidProfile id is required!");
        }

        reportService.removeLipidProfile(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllLipidProfileBy")
    public ResponseEntity<?> findAllLipidProfile(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<LipidProfile> lipidProfiles = reportService.getLipidProfileByPatientId(nic);

        return new ResponseEntity<>(lipidProfiles , HttpStatus.OK);
    }

    @GetMapping("/lipidProfile")
    public ResponseEntity<?> findLipidProfileById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        LipidProfile lipidProfiles = reportService.getLipidProfileById(id);

        return new ResponseEntity<>(lipidProfiles , HttpStatus.OK);
    }

    @GetMapping("/LipidProfileReport")
    public ResponseEntity<?> findLipidProfileById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getLipidProfileReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


    @PostMapping("/addAstAtl")
    public ResponseEntity<?> addAstAtl(@ModelAttribute AstAtlDto dto) throws FieldValidationException, HttpErrorException, IOException {

        if(dto.getReport() == null){
            throw new FieldValidationException("Report file is required!");
        }

        if (dto.getPatientId() == null) {
            throw new FieldValidationException("Patient is required!");
        }

        if (dto.getAlt() == null) {
            throw new FieldValidationException("ALT is required!");
        }

        if (dto.getAst() == null) {
            throw new FieldValidationException("AST is required!");
        }

        AstAtl astAtl = reportService.addAstAtl(dto.getReport() , dto);

        return new ResponseEntity<>(astAtl, HttpStatus.CREATED);

    }

    @PostMapping("/removeAstAtl")
    public ResponseEntity<?> removeAstAtl(@RequestParam("astAtl") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("AstAtl id is required!");
        }

        reportService.removeAstAtl(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAllAstAtlBy")
    public ResponseEntity<?> findAllAstAtl(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ArrayList<AstAtl> astAtls = reportService.getAstAtlByPatientId(nic);

        return new ResponseEntity<>(astAtls , HttpStatus.OK);
    }

    @GetMapping("/astAtl")
    public ResponseEntity<?> findAstAtlById(@RequestParam("id") Long id) throws FieldValidationException, IOException, HttpErrorException {
        if (id == null) {
            throw new FieldValidationException("ID is required");
        }

        AstAtl astAtl = reportService.getAstAtlById(id);

        return new ResponseEntity<>(astAtl , HttpStatus.OK);
    }

    @GetMapping("/AstAtlReport")
    public ResponseEntity<?> findAstAtlById(@RequestParam("nic") String nic) throws FieldValidationException, IOException, HttpErrorException {
        if (nic == null) {
            throw new FieldValidationException("NIC is required");
        }

        ReportData reportData = reportService.getAstAtlReport(nic);

        return new ResponseEntity<>(reportData , HttpStatus.OK);
    }


}
