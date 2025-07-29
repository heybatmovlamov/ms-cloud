package com.mscloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientLisResultDto {
    private Integer reportUId;
    private String patientName;
    private String blankName;
    private LocalDateTime registrationTime;
    private LocalDateTime approveTime;
    private LocalDateTime downloadTime;
    private Integer ago;
    private String secKey;
    private String qrCode;
}

