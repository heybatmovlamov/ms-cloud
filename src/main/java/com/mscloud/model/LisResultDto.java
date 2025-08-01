package com.mscloud.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LisResultDto {
    private Integer reportUid;
    private String patientName;
    private String blankName;
    private LocalDateTime registrationTime;
    private LocalDateTime analysisTime;
    private LocalDateTime confirmedTime;
    private Integer patientId;
    private String qrCode;
    private String lisFile;

}