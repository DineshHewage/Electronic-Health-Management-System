package com.sd.pms.app.dto;

import com.sd.pms.app.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientDto {
    private String firstName;
    private String lastName;
    private String nic;
    private String phoneNumber;
    private LocalDate dob;
    private BloodGroup bloodGroup;
}
