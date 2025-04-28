package com.sd.pms.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RecommendationType {
    DIABETIC_FOOT_CARE("Diabetic Foot Care", "Recommendation for diabetic foot care"),
    DIABETIC_RETINOPATHY_SCREENING("Diabetic Retinopathy Screening", "Recommendation for diabetic retinopathy screening"),
    DENTAL_SCREENING("Dental Screening", "Recommendation for dental screening"),
    FAMILY_SCREENING("Family Screening", "Recommendation for family screening");

    @Getter
    private final String displayText;

    @Getter
    private final String description;
}
