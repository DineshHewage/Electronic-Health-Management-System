package com.sd.pms.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String registrationNumber;
    private String phoneNumber;
    private String hospitalName;
}
