package com.sd.pms.app.dto;

import com.sd.pms.app.entity.Occurrence;
import com.sd.pms.app.entity.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecommendationDto {
    private String patientNic;
    private Occurrence occurrence;
    private String topic;
    private String recommendation;
    private RecommendationType recommendationType;
}
