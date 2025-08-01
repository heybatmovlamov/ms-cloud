package com.mscloud.service;

import com.mscloud.dao.repository.CloudRepository;
import com.mscloud.exception.CommonException;
import com.mscloud.exception.constant.ErrorCode;
import com.mscloud.model.CloudDto;
import com.mscloud.model.LisReportsInfoDto;
import com.mscloud.model.LisResultDto;
import com.mscloud.model.LocalDto;
import com.mscloud.model.PatientInfoDto;
import com.mscloud.model.PatientLisFileDto;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudService {

    private final CloudRepository repository;

    public List<Integer> upload(CloudDto cloudDto) {
        List<Integer> resultList = new ArrayList<>();
        for (LocalDto localDto : cloudDto.getCloudResponseList()) {
            PatientInfoDto patientInfo = localDto.getPatientInfo();
            for (LisReportsInfoDto lisDto : localDto.getLisReportsInfo()) {
                Integer resultId = repository.callLisRecordUploadFunction(
                        patientInfo.getPatientName(),
                        patientInfo.getSecretCode(),
                        patientInfo.getQrCode(),
                        lisDto.getUniqueId(),
                        lisDto.getSesId(),
                        lisDto.getPatientId(),
                        lisDto.getOrderId(),
                        lisDto.getBlankId(),
                        lisDto.getCooperativeId(),
                        lisDto.getFileName(),
                        lisDto.getRegistrationTime().toLocalDateTime(),
                        lisDto.getApproveTime().toLocalDateTime()
                );
                resultList.add(resultId);
            }
        }
        return resultList;
    }


    public List<LisResultDto> getLisResultBySecretAndQrCode(String secretCode, String qrCode) {
        List<Object[]> result = repository.getLisResultRaw(secretCode, qrCode);

        if (result == null || result.isEmpty() || result.stream().allMatch(this::isNotFound)) {
            throw new CommonException("-1", "Secret code və ya QR code yanlışdır");
        }

        List<LisResultDto> dtoList = result.stream()
                .filter(this::isValidRow)
                .map(this::safeMapToLisResultDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            throw new CommonException("-1", "Verilənlərdə uyğun məlumat tapılmadı");
        }

        return dtoList;
    }

    private boolean isValidRow(Object[] row) {
        return row.length >= 9 && !(row.length == 1 && row[0] instanceof Integer && ((Integer) row[0]) == -1);
    }

    private boolean isNotFound(Object[] row) {
        return row.length == 1 && row[0] instanceof Integer && ((Integer) row[0]) == -1;
    }

    private LocalDateTime toLocalDateTime(Object timestamp) {
        return timestamp instanceof Timestamp ? ((Timestamp) timestamp).toLocalDateTime() : null;
    }

    private LisResultDto safeMapToLisResultDto(Object[] row) {
        try {
            return LisResultDto.builder()
                    .reportUid(((Number) row[0]).intValue())
                    .patientName((String) row[1])
                    .blankName((String) row[2])
                    .registrationTime(toLocalDateTime(row[3]))
                    .analysisTime(toLocalDateTime(row[4]))
                    .confirmedTime(toLocalDateTime(row[5]))
                    .patientId(((Number) row[6]).intValue())
                    .qrCode((String) row[7])
                    .lisFile((String) row[8])
                    .build();
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            // Logger əlavə et
            return null;
        }
    }


    public PatientLisFileDto getLisFileByReportUidAndSecretAndQrCode(Integer reportUid, String secretCode,
                                                                     String qrCode) {
        return repository.getLisFileByReportUidAndSecretAndQrCode(reportUid, secretCode, qrCode);
    }

}