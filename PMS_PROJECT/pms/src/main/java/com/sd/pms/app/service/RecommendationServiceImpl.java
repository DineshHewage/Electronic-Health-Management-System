package com.sd.pms.app.service;

import com.sd.pms.app.dto.RecommendationDto;
import com.sd.pms.app.entity.Occurrence;
import com.sd.pms.app.entity.Patient;
import com.sd.pms.app.entity.Recommendation;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.repository.PatientRepository;
import com.sd.pms.app.repository.RecommendationRepository;
import com.sd.pms.app.util.FindJobFire;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{

    private final PatientRepository patientRepository;
    private final RecommendationRepository recommendationRepository;

    @Override
    public Recommendation createRecommendation(RecommendationDto dto) throws HttpErrorException {
        Patient patient = patientRepository.findByNicAndActive(dto.getPatientNic() , Boolean.TRUE).orElseThrow(()->new HttpErrorException("Patient not found"));

        Recommendation recommendation = new Recommendation();
        BeanUtils.copyProperties(dto,recommendation);
        recommendation.setActive(Boolean.TRUE);
        recommendation.setPatient(patient);
        recommendation.setReportedDate(LocalDate.now());
        recommendation.setNextFire(FindJobFire.createNextFireDate(recommendation));

        this.setToDeActiveAll(recommendation.getPatient().getNic(),recommendation.getOccurrence());
        recommendationRepository.save(recommendation);

        return recommendation;
    }

    @Override
    public List<Recommendation> findByPatientNicAndOccurrence(String nic , Occurrence occurrence) {
        return recommendationRepository.findAllByPatientNicAndOccurrence(nic, occurrence);
    }

    @Override
    public void activeRecommendation(Long id) throws HttpErrorException {
        Recommendation recommendation =  recommendationRepository.findById(id).orElseThrow(()->new HttpErrorException("Patient not found"));
        this.setToDeActiveAll(recommendation.getPatient().getNic(),recommendation.getOccurrence());
        recommendation.setActive(Boolean.TRUE);
        recommendationRepository.save(recommendation);
    }

    @Override
    public void deActiveRecommendation(Long id) throws HttpErrorException {
        Recommendation recommendation =  recommendationRepository.findById(id).orElseThrow(()->new HttpErrorException("Patient not found"));
        this.setToDeActiveAll(recommendation.getPatient().getNic(),recommendation.getOccurrence());
    }

    private void setToDeActiveAll(String nic , Occurrence occurrence){
        List<Recommendation> recommendationList = recommendationRepository.findAllByPatientNicAndOccurrence(nic,occurrence);

        recommendationList.stream().forEach(e ->{
            e.setActive(Boolean.FALSE);
            recommendationRepository.save(e);
        });
    }
}
