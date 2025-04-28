package com.sd.pms.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminDto {
    private String firstName;
    private String lastName;
    private String nic;
    private String phoneNumber;
    private String hospitalName;
}
