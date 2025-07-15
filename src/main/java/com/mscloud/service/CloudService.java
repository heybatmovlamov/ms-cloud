package com.mscloud.service;

import com.mscloud.dao.repository.CloudRepository;
import com.mscloud.model.CloudDto;
import com.mscloud.model.LisReportsInfoDto;
import com.mscloud.model.LocalDto;
import com.mscloud.model.PatientInfoDto;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudService {

    private final CloudRepository repository;

//    public Integer upload(CloudDto cloudDto) {
//        LocalDto dto = cloudDto.getCloudResponseList().getFirst();
//        PatientInfoDto patientInfo = dto.getPatientInfo();
//        List<LisReportsInfoDto> lisReportsInfo = dto.getLisReportsInfo();
//        LisReportsInfoDto lisDto = lisReportsInfo.get(0);
//
//        return repository.callLisRecordUploadFunction(patientInfo.getPatientName(),
//                patientInfo.getSecretCode(),
//                patientInfo.getQrCode(),
//                lisDto.getUniqueId(),
//                lisDto.getSesId(),
//                lisDto.getPatientId(),
//                lisDto.getOrderId(),
//                lisDto.getBlankId(),
//                lisDto.getCooperativeId(),
//                lisDto.getFileName(),
//                lisDto.getRegistrationTime(),
//                lisDto.getApproveTime());
//    }

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
                        lisDto.getRegistrationTime(),
                        lisDto.getApproveTime()
                );
                resultList.add(resultId);
            }
        }
        return resultList;
    }

}
