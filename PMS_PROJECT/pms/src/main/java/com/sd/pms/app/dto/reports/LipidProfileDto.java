package com.sd.pms.app.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LipidProfileDto {
    private Long patientId;
    private Double hdl;
    private Double ldl;
    private Double vldl;
    private Double tag;
    private Double chdl;
    MultipartFile report;
}
