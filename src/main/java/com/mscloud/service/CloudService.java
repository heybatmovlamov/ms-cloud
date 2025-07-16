package com.mscloud.service;

import com.mscloud.dao.repository.CloudRepository;
import com.mscloud.model.CloudDto;
import com.mscloud.model.LisReportsInfoDto;
import com.mscloud.model.LocalDto;
import com.mscloud.model.PatientInfoDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );
                resultList.add(resultId);
            }
        }
        return resultList;
    }

}
