package com.sd.pms.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_RECOMMENDATION")
public class Recommendation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "PATIENT")
    private Patient patient;

    @Column(name = "REPORTED_DATE")
    private LocalDate reportedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "OCCURRENCE")
    private Occurrence occurrence;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Enumerated(EnumType.STRING)
    @Column(name = "RECOMMENDATION_TYPE")
    private RecommendationType recommendationType;

    @Column(name = "LAST_FIRE")
    private LocalDate lastFire;

    @Column(name = "NEXT_FIRE")
    private LocalDate nextFire;

    @Column(name = "ACTIVE")
    private Boolean active;

}
