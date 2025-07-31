package com.mscloud.dao.repository;

import com.mscloud.dao.entity.PatientEntity;
import com.mscloud.model.PatientLisFileDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CloudRepository extends JpaRepository<PatientEntity, Long> {

    @Query(value = "SELECT get_lis_record_uid_oncloud(:patientName, :secretCode, :qrCode, :uId, :sesId, :pId, :orderId, :blankId, :cId, :fileName, :registrationTime, :approveTime)", nativeQuery = false)
    Integer callLisRecordUploadFunction(
            @Param("patientName") String patientName,
            @Param("secretCode") String secretCode,
            @Param("qrCode") String qrCode,
            @Param("uId") Integer uId,
            @Param("sesId") Integer sesId,
            @Param("pId") Integer pId,
            @Param("orderId") Integer orderId,
            @Param("blankId") Integer blankId,
            @Param("cId") Integer cId,
            @Param("fileName") String fileName,
            @Param("registrationTime") LocalDateTime registrationTime,
            @Param("approveTime") LocalDateTime approveTime
    );

    @Query(value = "CALL get_patient_lisresult_via_secretkey(:secretCode, :qrCode)", nativeQuery = true)
    List<Object[]> getLisResultRaw(@Param("secretCode") String secretCode, @Param("qrCode") String qrCode);

    @Query(value = "CALL get_patient_lisfile_via_secretkey(:reportUid, :secretCode, :qrCode)", nativeQuery = true)
    PatientLisFileDto getLisFileByReportUidAndSecretAndQrCode(
            @Param("reportUid") Integer reportUid,
            @Param("secretCode") String secretCode,
            @Param("qrCode") String qrCode
    );
}