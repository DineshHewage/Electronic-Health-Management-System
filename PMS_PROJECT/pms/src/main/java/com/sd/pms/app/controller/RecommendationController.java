package com.sd.pms.app.controller;

import com.sd.pms.app.dto.RecommendationDto;
import com.sd.pms.app.entity.Occurrence;
import com.sd.pms.app.entity.Recommendation;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/create")
    public ResponseEntity<?> createRecommendation(@RequestBody RecommendationDto dto) throws HttpErrorException {

        if(dto.getPatientNic() == null || dto.getPatientNic().equals("")){
            throw new HttpErrorException("Patient NIC is required");
        }

        if(dto.getOccurrence() == null){
            throw new HttpErrorException("Occurrence is required");
        }

        if(dto.getTopic() == null || dto.getTopic().equals("")){
            throw new HttpErrorException("Topic is required");
        }

        if(dto.getRecommendation() == null || dto.getRecommendation().equals("")){
            throw new HttpErrorException("Recommendation is required");
        }

        if(dto.getRecommendationType() == null){
            throw new HttpErrorException("Recommendation type is required");
        }

        Recommendation recommendation = recommendationService.createRecommendation(dto);
        return new ResponseEntity<>(recommendation, HttpStatus.CREATED);
    }

    @GetMapping("/findByPatientNicAndOccurrence")
    public ResponseEntity<?> findByPatientNicAndOccurrence(@RequestParam("nic") String nic ,@RequestParam("occurrence") Occurrence occurrence){
        List<Recommendation> recommendationList = recommendationService.findByPatientNicAndOccurrence(nic,occurrence);
        return new ResponseEntity<>(recommendationList,HttpStatus.OK);
    }

    @GetMapping("/activeRecommendation")
    public void activeRecommendation(@RequestParam("id") Long id) throws HttpErrorException{
        recommendationService.activeRecommendation(id);
    }

    @GetMapping("/deActiveRecommendation")
    void deActiveRecommendation(@RequestParam("id") Long id) throws HttpErrorException{
        recommendationService.deActiveRecommendation(id);
    }

}
