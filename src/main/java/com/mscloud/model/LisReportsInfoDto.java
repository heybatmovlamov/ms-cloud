package com.mscloud.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LisReportsInfoDto {

    private Integer uniqueId;
    private Integer sesId;
    private Integer orderId;
    private Integer blankId;
    private Integer cooperativeId;
    private Integer patientId;
    private String fileName;
    private Timestamp registrationTime;
    private Timestamp approveTime;
}
