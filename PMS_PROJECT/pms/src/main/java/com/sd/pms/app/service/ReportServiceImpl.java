package com.sd.pms.app.service;

import com.sd.pms.app.dto.reports.*;
import com.sd.pms.app.entity.Patient;
import com.sd.pms.app.entity.reports.*;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final FbsRepository fbsRepository;
    private final PatientRepository patientRepository;
    private final BloodPressureRepository bloodPressureRepository;
    private final DietPlanRepository dietPlanRepository;
    private final WBmiRepository wBmiRepository;
    private final SerumCreatinineRepository serumCreatinineRepository;
    private final Hba1c6Repository hba1c6Repository;
    private final LipidProfileRepository lipidProfileRepository;
    private final AstAtlRepository astAtlRepository;

    @Value("${file.report.dir}")
    private String fileUploadDir;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Fbs addFbs(MultipartFile file, FbsDto dto) throws IOException, HttpErrorException {

        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        Fbs fbs = Fbs.builder()
                .fbs(dto.getFbs())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(dto.getReportedDate())
                .build();

        String filePath = saveFile(file , fbs);
        fbs.setFileDir(filePath);

        log.info("Fbs saved : {}" , fbs.toString());
        fbsRepository.save(fbs);

        return fbs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeFbs(Long id) throws HttpErrorException, IOException {

        Fbs fbs = fbsRepository.findById(id).orElseThrow(() ->new HttpErrorException("FBS not found"));
        fbs.setActive(Boolean.FALSE);
        fbsRepository.save(fbs);

        deleteFileByName(fbs.getFileDir());
    }

    @Override
    public ArrayList getFbsByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) fbsRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public Fbs getFbsById(Long id) throws HttpErrorException, IOException {
        Fbs fbs = fbsRepository.findById(id).orElseThrow(() ->new HttpErrorException("FBS not found"));
        return fbs;
    }

    @Override
    public ReportData getFbsReport(String nic) throws HttpErrorException, IOException {
        ArrayList<Fbs> dataAll = (ArrayList<Fbs>) fbsRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getFbs());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }

    @Override
    public BloodPressure addBp(MultipartFile file, BloodPressureDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        BloodPressure bp = BloodPressure.builder()
                .bp(dto.getBp())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();

        String filePath = saveFile(file , bp);
        bp.setFileDir(filePath);

        log.info("Bp saved : {}" , bp.toString());
        bloodPressureRepository.save(bp);

        return bp;
    }

    @Override
    public void removeBp(Long id) throws HttpErrorException, IOException {
        BloodPressure bp = bloodPressureRepository.findById(id).orElseThrow(() ->new HttpErrorException("BP not found"));
        bp.setActive(Boolean.FALSE);
        bloodPressureRepository.save(bp);
        log.info("Bp deleted : {}" , bp.toString());
        deleteFileByName(bp.getFileDir());
    }

    @Override
    public ArrayList getBpByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) bloodPressureRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public BloodPressure getBpById(Long id) throws HttpErrorException, IOException {
        return bloodPressureRepository.findById(id).orElseThrow(() ->new HttpErrorException("BP not found"));
    }

    @Override
    public ReportData getBpReport(String nic) throws HttpErrorException, IOException {
        ArrayList<BloodPressure> dataAll = (ArrayList<BloodPressure>) bloodPressureRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getBp());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }

    @Override
    public DietPlan addDietPlan(MultipartFile file, DietDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        DietPlan dietPlan = DietPlan.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(dto.getReportedDate())
                .build();

        String filePath = saveFile(file , dietPlan);
        dietPlan.setFileDir(filePath);

        log.info("Diet saved : {}" , dietPlan.toString());
        dietPlanRepository.save(dietPlan);

        return dietPlan;
    }

    @Override
    public void removeDietPlan(Long id) throws HttpErrorException, IOException {
        DietPlan dietPlan = dietPlanRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
        dietPlan.setActive(Boolean.FALSE);
        dietPlanRepository.save(dietPlan);
        log.info("Diet deleted : {}" , dietPlan.toString());
        deleteFileByName(dietPlan.getFileDir());
    }

    @Override
    public ArrayList getDietByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) dietPlanRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public DietPlan getDietById(Long id) throws HttpErrorException, IOException {
        return dietPlanRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getDietReport(String nic) throws HttpErrorException, IOException {
        return null;
    }

    @Override
    public WBmi addWBmi(MultipartFile file, WBmiDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        WBmi wBmi = WBmi.builder()
                .weight(dto.getWeight())
                .bmi(dto.getBmi())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();

        String filePath = saveFile(file , wBmi);
        wBmi.setFileDir(filePath);

        log.info("WBmi saved : {}" , wBmi.toString());
        wBmiRepository.save(wBmi);

        return wBmi;
    }

    @Override
    public void removeWBmi(Long id) throws HttpErrorException, IOException {
        WBmi wBmi = wBmiRepository.findById(id).orElseThrow(() ->new HttpErrorException("WBmi not found"));
        wBmi.setActive(Boolean.FALSE);
        wBmiRepository.save(wBmi);
        log.info("WBmi deleted : {}" , wBmi.toString());
        deleteFileByName(wBmi.getFileDir());
    }

    @Override
    public ArrayList getWBmiByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) wBmiRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public WBmi getWBmiById(Long id) throws HttpErrorException, IOException {
        return wBmiRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getWBmiReport(String nic) throws HttpErrorException, IOException {
        ArrayList<WBmi> dataAll = (ArrayList<WBmi>) wBmiRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getBmi());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }

    @Override
    public SerumCreatinine addSerumCreatinine(MultipartFile file, SerumCreatinineDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        SerumCreatinine serumCreatinine = SerumCreatinine.builder()
                .serumCreatinine(dto.getSerumCreatinine())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();

        String filePath = saveFile(file , serumCreatinine);
        serumCreatinine.setFileDir(filePath);

        log.info("WBmi saved : {}" , serumCreatinine.toString());
        serumCreatinineRepository.save(serumCreatinine);

        return serumCreatinine;
    }

    @Override
    public void removeSerumCreatinine(Long id) throws HttpErrorException, IOException {
        SerumCreatinine serumCreatinine = serumCreatinineRepository.findById(id).orElseThrow(() ->new HttpErrorException("SerumCreatinine not found"));
        serumCreatinine.setActive(Boolean.FALSE);
        serumCreatinineRepository.save(serumCreatinine);
        log.info("SerumCreatinine deleted : {}" , serumCreatinine.toString());
        deleteFileByName(serumCreatinine.getFileDir());
    }

    @Override
    public ArrayList getSerumCreatinineByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) serumCreatinineRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public SerumCreatinine getSerumCreatinineById(Long id) throws HttpErrorException, IOException {
        return serumCreatinineRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getSerumCreatinineReport(String nic) throws HttpErrorException, IOException {
        ArrayList<SerumCreatinine> dataAll = (ArrayList<SerumCreatinine>) serumCreatinineRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getSerumCreatinine());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }

    @Override
    public Hba1c6 addHba1c6(MultipartFile file, Hba1c6Dto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        Hba1c6 hba1c6 = Hba1c6.builder()
                .hba1c6(dto.getHba1c6())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();

        String filePath = saveFile(file , hba1c6);
        hba1c6.setFileDir(filePath);

        log.info("Hba1c6 saved : {}" , hba1c6.toString());
        hba1c6Repository.save(hba1c6);

        return hba1c6;
    }

    @Override
    public void removeHba1c6(Long id) throws HttpErrorException, IOException {
        Hba1c6 hba1c6 = hba1c6Repository.findById(id).orElseThrow(() ->new HttpErrorException("Hba1c6 not found"));
        hba1c6.setActive(Boolean.FALSE);
        hba1c6Repository.save(hba1c6);
        log.info("Hba1c6 deleted : {}" , hba1c6.toString());
        deleteFileByName(hba1c6.getFileDir());
    }

    @Override
    public ArrayList getHba1c6ByPatientId(String id) throws HttpErrorException, IOException {
        return (ArrayList) hba1c6Repository.findAllByPatientNicAndActive(id , Boolean.TRUE);
    }

    @Override
    public Hba1c6 getHba1c6ById(Long id) throws HttpErrorException, IOException {
        return hba1c6Repository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getHba1c6Report(String nic) throws HttpErrorException, IOException {
        ArrayList<Hba1c6> dataAll = (ArrayList<Hba1c6>) hba1c6Repository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getHba1c6());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }


    @Override
    public LipidProfile addLipidProfile(MultipartFile file, LipidProfileDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        LipidProfile lipidProfile = LipidProfile.builder()
                .ldl(dto.getLdl())
                .hdl(dto.getHdl())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();
        BeanUtils.copyProperties(dto , lipidProfile);

        String filePath = saveFile(file , lipidProfile);
        lipidProfile.setFileDir(filePath);

        log.info("LipidProfile saved : {}" , lipidProfile.toString());
        lipidProfileRepository.save(lipidProfile);

        return lipidProfile;
    }

    @Override
    public void removeLipidProfile(Long id) throws HttpErrorException, IOException {
        LipidProfile lipidProfile = lipidProfileRepository.findById(id).orElseThrow(() ->new HttpErrorException("LipidProfile not found"));
        lipidProfile.setActive(Boolean.FALSE);
        lipidProfileRepository.save(lipidProfile);
        log.info("LipidProfile deleted : {}" , lipidProfile.toString());
        deleteFileByName(lipidProfile.getFileDir());
    }

    @Override
    public ArrayList getLipidProfileByPatientId(String id) throws HttpErrorException, IOException {
        return (ArrayList) lipidProfileRepository.findAllByPatientNicAndActive(id , Boolean.TRUE);
    }

    @Override
    public LipidProfile getLipidProfileById(Long id) throws HttpErrorException, IOException {
        return lipidProfileRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getLipidProfileReport(String nic) throws HttpErrorException, IOException {
        ArrayList<LipidProfile> dataAll = (ArrayList<LipidProfile>) lipidProfileRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getChdl());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }

    @Override
    public AstAtl addAstAtl(MultipartFile file, AstAtlDto dto) throws IOException, HttpErrorException {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new HttpErrorException("Patient not found"));

        AstAtl astAtl = AstAtl.builder()
                .ast(dto.getAst())
                .alt(dto.getAlt())
                .fileName(file.getName())
                .patient(patient)
                .active(Boolean.TRUE)
                .reportedDate(LocalDate.now())
                .build();

        String filePath = saveFile(file , astAtl);
        astAtl.setFileDir(filePath);

        log.info("AstAtl saved : {}" , astAtl.toString());
        astAtlRepository.save(astAtl);

        return astAtl;
    }

    @Override
    public void removeAstAtl(Long id) throws HttpErrorException, IOException {
        AstAtl astAtl = astAtlRepository.findById(id).orElseThrow(() ->new HttpErrorException("AstAtl not found"));
        astAtl.setActive(Boolean.FALSE);
        astAtlRepository.save(astAtl);
        log.info("AstAtl deleted : {}" , astAtl.toString());
        deleteFileByName(astAtl.getFileDir());
    }

    @Override
    public ArrayList getAstAtlByPatientId(String nic) throws HttpErrorException, IOException {
        return (ArrayList) astAtlRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
    }

    @Override
    public AstAtl getAstAtlById(Long id) throws HttpErrorException, IOException {
        return astAtlRepository.findById(id).orElseThrow(() ->new HttpErrorException("Diet not found"));
    }

    @Override
    public ReportData getAstAtlReport(String nic) throws HttpErrorException, IOException {
        ArrayList<AstAtl> dataAll = (ArrayList<AstAtl>) astAtlRepository.findAllByPatientNicAndActive(nic , Boolean.TRUE);
        ReportData reportData = new ReportData();
        ArrayList<String > labels = new ArrayList<>();
        ArrayList<Double > data  = new ArrayList<>();
        dataAll.stream().forEach(e -> {
            labels.add(e.getReportedDate().toString());
            data.add(e.getAst());
        });
        reportData.setData(data);
        reportData.setLabels(labels);
        return reportData;
    }


    @Override
    public String saveFile(MultipartFile file , Report report) throws IOException {

        // Rename the file to a timestamp
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = Instant.now().toEpochMilli() + "_" + originalFileName;

        // Save the file content to the file system
        Path uploadPath = Path.of(fileUploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("File saved at path: {}", filePath.toString());
        return filePath.toString();
    }

    @Override
    public void deleteFileByName(String filePathToDelete) throws IOException {

        if (filePathToDelete != null) {
            // Delete the file from the file system
            Path filePath = Path.of(filePathToDelete);
            Files.deleteIfExists(filePath);

            // Log the deletion
            log.info("File deleted at path: {}", filePath);

        } else {
            log.warn("File with name {} not found. Deletion skipped.", filePathToDelete);
        }
    }
}
