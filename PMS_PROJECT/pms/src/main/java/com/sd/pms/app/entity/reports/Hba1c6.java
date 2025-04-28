package com.sd.pms.app.entity.reports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.pms.app.entity.BaseEntity;
import com.sd.pms.app.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TBL_HBA1C6")
public class Hba1c6 extends BaseEntity implements Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "PATIENT")
    private Patient patient;

    @Column(name = "REPORTED_DATE")
    private LocalDate reportedDate;




    @Column(name = "HBA1C6")
    private Double hba1c6;




    @Column(name = "FILE_DIR")
    private String fileDir;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ACTIVE")
    private Boolean active;


    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
