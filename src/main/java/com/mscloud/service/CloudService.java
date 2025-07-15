package com.mscloud.service;

import com.mscloud.dao.repository.CloudRepository;
import com.mscloud.exception.DuplicateRecordException;
import com.mscloud.exception.MissingInputException;
import com.mscloud.model.CloudDto;
import com.mscloud.model.LisReportsInfoDto;
import com.mscloud.model.LocalDto;
import com.mscloud.model.PatientInfoDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mscloud.exception.constant.ErrorMessage.DUPLICATE_RECORD;
import static com.mscloud.exception.constant.ErrorMessage.MISSING_INPUT;

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
                        lisDto.getRegistrationTime(),
                        lisDto.getApproveTime()
                );
                if (resultId == -1) {
                    throw MissingInputException.of(MISSING_INPUT, resultId);

                } else if (resultId == -2) {
                    throw DuplicateRecordException.of(DUPLICATE_RECORD, resultId);
                }
                resultList.add(resultId);

            }
        }
        return resultList;
    }

}
