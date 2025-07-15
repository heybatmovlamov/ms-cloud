package com.mscloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoDto {

    private String patientName;
    private Integer secretCode;
    private Integer qrCode;
}
