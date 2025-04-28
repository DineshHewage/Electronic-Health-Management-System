package com.sd.pms.app.service;

import com.sd.pms.app.dto.RecommendationDto;
import com.sd.pms.app.entity.Occurrence;
import com.sd.pms.app.entity.Recommendation;
import com.sd.pms.app.exceptions.HttpErrorException;

import java.util.List;

public interface RecommendationService {
    Recommendation createRecommendation(RecommendationDto dto) throws HttpErrorException;
    List<Recommendation> findByPatientNicAndOccurrence(String nic , Occurrence occurrence);
    void activeRecommendation(Long id) throws HttpErrorException;
    void deActiveRecommendation(Long id) throws HttpErrorException;
}
